package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.MoreExecutors.getExitingExecutorService;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static uk.co.bbc.iplayer.common.concurrency.EvenMoreExecutors.boundedNamedCachedExecutorService;

public final class MoreFutures2 {

    public static final Duration DEFAULT_DURATION = Duration.create();
    public static final boolean INTERRUPT_TASK = true;

    private MoreFutures2() {
        throw new AssertionError();
    }

    /**
     * @param token
     * @param <T>
     * @return
     */
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

        // Or use caller runs policy?
        private static class DefaultExecutorServiceFactory {
            public static final int MAX_THREAD_BOUND = (Runtime.getRuntime().availableProcessors() + 1);
            public static final ListeningExecutorService EXECUTOR_SERVICE =
                    listeningDecorator(
                            getExitingExecutorService((ThreadPoolExecutor) boundedNamedCachedExecutorService(MAX_THREAD_BOUND, "MoreFutureDefault")));
        }

        /**
         * @param task
         * @return
         */
        public FutureDSL<T> add(final Callable<T> task) {
            checkNotNull(task);
            futureSuppliers.add(new Supplier<ListenableFuture<T>>() {
                @Override
                public ListenableFuture<T> get() {
                    return executorService.get().submit(task);
                }
            });
            return this;
        }

        /**
         * @param collection
         * @return
         */
        public FutureDSL<T> addAll(Collection<Callable<T>> collection) {
            checkNotNull(collection);
            for (Callable<T> callable : collection) {
                add(callable);
            }
            return this;
        }

        /**
         * @param executorService
         * @return
         */
        public FutureDSL<T> using(ExecutorService executorService) {

            checkNotNull(executorService, "executor must not be null");

            this.executorService = Optional.of(
                    MoreExecutors.listeningDecorator(executorService));

            return this;
        }

        /**
         * @param toThrow
         * @param <E>
         * @return
         */
        public <E extends Exception> FutureDSL<T> onExceptionThrow(Class<E> toThrow) {
            this.onExceptionThrow = toThrow;
            return this;
        }

        /**
         * @param future
         * @return
         */
        public FutureDSL<T> add(final ListenableFuture<T> future) {
            futureSuppliers.add(Suppliers.ofInstance(future));
            return this;
        }

        /**
         * @param aggregator
         * @return
         * @throws Exception
         */
        public List<T> aggregate(ThrowableFunction<List<ListenableFuture<T>>, List<T>> aggregator) throws Exception {
            return aggregator.apply(createFutureList());
        }

        /**
         * @return
         * @throws Exception
         */
        public List<T> aggregate() throws Exception {
            return aggregate(new Atomic<T>(onExceptionThrow));
        }

        /**
         * @param function
         * @param <S>
         * @return
         * @throws Exception
         */
        public <S> S aggregate(ThrowableFunction<List<T>, S> function) throws Exception {
            return function.apply(aggregate());
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

    /**
     * @param <T>
     */
    public static class FilterSuccessful<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {

        private final Class<? extends Exception> toThrow;

        public FilterSuccessful(Class<? extends Exception> toThrow) {
            this.toThrow = toThrow;
        }

        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            ListenableFuture<List<T>> listenableFuture = Futures.successfulAsList(input);
            return await(listenableFuture, Duration.create(), toThrow);
        }
    }

    /**
     * @param <T>
     */
    public static class Atomic<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {

        private Class<? extends Exception> toThrow = MoreFuturesException.class;

        public Atomic(Class<? extends Exception> toThrow) {
            this.toThrow = toThrow;
        }

        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            ListenableFuture<List<T>> listenableFuture = Futures.allAsList(input);
            return await(listenableFuture, Duration.create(), toThrow);
        }
    }

    public static <T> T await(ListenableFuture<? extends T> future) throws MoreFuturesException {
        return await(future, DEFAULT_DURATION, MoreFuturesException.class);
    }

    public static <T, E extends Exception> T await(ListenableFuture<? extends T> future, Duration duration, Class<E> exception) throws E {
        try {
            return Futures.get(
                    future,
                    duration.getLength(),
                    duration.getTimeUnit(),
                    exception
            );
        } finally {
            if (!future.isDone()) {
                future.cancel(INTERRUPT_TASK);
            }
        }
    }

    public static <T> PipeableFuture<T> pipe(ListenableFuture<T> future) {
        return PipeableFutureTask.create(future);
    }
}