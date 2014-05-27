package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.util.concurrent.MoreExecutors.getExitingExecutorService;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static uk.co.bbc.iplayer.common.concurrency.EvenMoreExecutors.boundedNamedCachedExecutorService;

public final class MoreFutures {

    public static final boolean INTERRUPT_TASK = true;
    public static final Duration DEFAULT_DURATION = Duration.create();
    private static final Logger LOG = LoggerFactory.getLogger(MoreFutures.class);

    private MoreFutures() {
        throw new AssertionError();
    }

    public static <T> FutureDSL<T> composeFuturesOf(Class<T> token) {
        return new FutureDSL<T>();
    }

    /**
     * Future Aggregation builder (DSL)
     *
     * @param <T>
     */
    public static final class FutureDSL<T> {

        private Class<? extends Exception> onExceptionThrow = MoreFuturesException.class;
        private Optional<ListeningExecutorService> executorService = Optional.absent();
        private final List<Supplier<ListenableFuture<T>>> futureSuppliers = Lists.newArrayList();
        private final List<ListenableFuture<T>> futures = Lists.newArrayList();
        private final AtomicBoolean futuresConstructed = new AtomicBoolean(false);
        private Optional<Duration> duration = Optional.absent();

        // Or use caller runs policy?
        private static class DefaultExecutorServiceFactory {
            public static final int MAX_THREAD_BOUND = (Runtime.getRuntime().availableProcessors() + 1);
            public static final ListeningExecutorService EXECUTOR_SERVICE =
                    listeningDecorator(
                            getExitingExecutorService((ThreadPoolExecutor) boundedNamedCachedExecutorService(MAX_THREAD_BOUND, "MoreFutureDefault")));
        }

        public FutureDSL<T> createFuture(final Callable<T> task) {
            checkNotNull(task);
            futureSuppliers.add(new Supplier<ListenableFuture<T>>() {
                @Override
                public ListenableFuture<T> get() {
                    return executorService.get().submit(task);
                }
            });
            return this;
        }

        public FutureDSL<T> createFutures(Collection<Callable<T>> collection) {
            checkNotNull(collection);
            for (Callable<T> callable : collection) {
                createFuture(callable);
            }
            return this;
        }

        public FutureDSL<T> using(ExecutorService executorService) {

            this.executorService = Optional.of(
                    MoreExecutors.listeningDecorator(checkNotNull(executorService, "executor must not be null")));

            return this;
        }

        public <E extends Exception> FutureDSL<T> onExceptionThrow(Class<E> toThrow) {
            this.onExceptionThrow = toThrow;
            return this;
        }

        public <E extends Exception> FutureDSL<T> duration(Duration duration) {
            this.duration = Optional.of(duration);
            return this;
        }

        public FutureDSL<T> addFuture(final ListenableFuture<T> future) {
            futureSuppliers.add(Suppliers.ofInstance(future));
            return this;
        }

        public FutureDSL<T> addAllFutures(Collection<ListenableFuture<T>> collection) {
            checkNotNull(collection);
            for (ListenableFuture<T> callable : collection) {
                addFuture(callable);
            }
            return this;
        }

        public List<T> aggregateAndTransform(ThrowableFunction<List<ListenableFuture<T>>, List<T>> aggregator) throws Exception {
            return aggregator.apply(createFutureList());
        }

        <S> S aggregateAndTransform(ThrowableFunction<List<T>, S> function) throws Exception {
            return function.apply(aggregate());
        }

        public List<T> aggregate() throws Exception {
            return aggregateAndTransform(new Atomic<T>(onExceptionThrow));
        }

        public PipeableFuture<List<T>> asFuture() throws Exception {
            List<ListenableFuture<T>> futureList = createFutureList();
            return pipe(Futures.successfulAsList(futureList));
        }

        private List<ListenableFuture<T>> createFutureList() {

            if (!futuresConstructed.get()) {
                // do we need a default executor (with automatic shutdown)?
                if (!executorService.isPresent()) {
                    executorService = Optional.of(DefaultExecutorServiceFactory.EXECUTOR_SERVICE);
                }

                for (Supplier<ListenableFuture<T>> supplier : futureSuppliers) {
                    futures.add(supplier.get());
                }
                futuresConstructed.set(true);
            }

            return futures;
        }
    }

    public static class FilterSuccessful<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {
        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            ListenableFuture<List<T>> pending = Futures.successfulAsList(input);
            // filter nulls and return list
            return Lists.newArrayList(filter(await(pending), notNull()));
        }
    }

    public static class Atomic<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {

        private Class<? extends Exception> toThrow = MoreFuturesException.class;

        public Atomic(Class<? extends Exception> toThrow) {
            this.toThrow = toThrow;
        }

        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            ListenableFuture<List<T>> listenableFuture = Futures.allAsList(input);
            return awaitOrThrow(listenableFuture, toThrow);
        }
    }

    public static <T> PipeableFuture<T> pipe(ListenableFuture<T> future) {
        return PipeableFutureTask.create(future);
    }

    public static <T, E extends Exception> T await(ListenableFuture<? extends T> future, Duration duration) throws MoreFuturesException {

        try {
            return future.get(duration.getLength(), duration.getTimeUnit());

        } catch (InterruptedException e) {
            logExceptionMessage("await interrupted", e);
            throw new MoreFuturesException("Future interrupted", e);

        } catch (ExecutionException e) {
            logExceptionMessage("await Execution", e);
            throw new MoreFuturesException("Future Execution Exception", e);

        } catch (TimeoutException e) {
            logExceptionMessage("await timeout", e);
            throw new MoreFuturesException("Timed out", e);

        } finally {
            if (!future.isDone()) {
                future.cancel(INTERRUPT_TASK);
            }
        }
    }

    public static <T, E extends Exception> T await(ListenableFuture<? extends T> future) throws MoreFuturesException {
        return await(future, DEFAULT_DURATION);
    }

    @Deprecated
    public static <T> List<T> aggregate(Iterable<? extends ListenableFuture<? extends T>> futures, Duration timeout) throws MoreFuturesException {
        throw new UnsupportedOperationException();
    }

    public static <T, EX extends Exception> T awaitOrThrow(ListenableFuture<T> future, Class<EX> toThrow) throws EX {
        try {
            return await(future, DEFAULT_DURATION);

        } catch (MoreFuturesException e) {
            EX instance = initException(toThrow, e);
            throw instance;
        }
    }

    private static <EX> EX initException(Class<EX> toThrow, Throwable sourceException) {

        Constructor[] allConstructors = toThrow.getDeclaredConstructors();
        for (Constructor constructor : allConstructors) {

            List<Object> constructorParameters = Lists.newArrayList();

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i].equals(String.class)) {
                    constructorParameters.add(sourceException.getMessage());

                } else if (parameterTypes[i].equals(Throwable.class)) {
                    constructorParameters.add(sourceException);
                }
            }

            try {
                return (EX) constructor.newInstance(constructorParameters.toArray());
            } catch (InstantiationException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }
        }

        throw new IllegalArgumentException("Can't find constructor for " + toThrow);
    }

    private static void logExceptionMessage(String method, Exception e) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(method + "," + ExceptionUtils.getFullStackTrace(e));
        }
    }

    private static <T> void cancelActiveFutures(final Iterable<? extends ListenableFuture<? extends T>> futures) {
        synchronized (futures) {
            for (ListenableFuture future : futures) {
                if (!future.isDone()) {
                    future.cancel(INTERRUPT_TASK);
                }
            }
        }
    }

    private static <T> List<T> filterCompleteTasks(Iterable<? extends ListenableFuture<? extends T>> futures) {

        List<T> completedTasks = Lists.newArrayList();
        for (ListenableFuture<? extends T> future : futures) {
            // completed and has not been terminated
            if (future.isDone() && !future.isCancelled()) {
                T value = null;
                try {
                    value = MoreFutures.await(future);
                } catch (MoreFuturesException moreFuturesException) {
                    logExceptionMessage("filterCompleteTasks", moreFuturesException);
                }
                completedTasks.add(value);
            }
        }

        return completedTasks;
    }
}