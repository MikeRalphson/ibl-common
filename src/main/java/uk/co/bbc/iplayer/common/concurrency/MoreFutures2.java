package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Optional;
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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static uk.co.bbc.iplayer.common.concurrency.EvenMoreExecutors.*;

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
    public static <T> Builder<T> chain(Class<T> token) {
        return new Builder<T>();
    }

    /**
     *
     * Future Aggregation builder (DSL)
     *
     * @param <T>
     */
    public static class Builder<T> {

        private Optional<ExecutorService> executorService = Optional.absent();
        private ThrowableFunction<ListenableFuture<List<T>>, List<T>> aggregator;
        private List<Callable<T>> tasks = Lists.newArrayList();
        private Class<? extends Exception> onExceptionThrow = MoreFuturesException.class;

        // Or use caller runs policy?
        private static class DefaultExecutorServiceFactory {
            public static final int MAX_THREAD_BOUND = (Runtime.getRuntime().availableProcessors() + 1);
            public static ExecutorService ExecutorService =
                    MoreExecutors.getExitingExecutorService((ThreadPoolExecutor) boundedNamedCachedExecutorService(MAX_THREAD_BOUND, "MoreFutureDefault"));
        }

        /**
         * @param task
         * @return
         */
        public Builder<T> add(Callable<T> task) {
            tasks.add(checkNotNull(task));
            return this;
        }

        /**
         * @param collection
         * @return
         */
        public Builder<T> addAll(Collection<Callable<T>> collection) {
            tasks.addAll(checkNotNull(collection));
            return this;
        }

        /**
         * @param executorService
         * @return
         */
        public Builder<T> usingExecutorService(ExecutorService executorService) {
            this.executorService = Optional.of(checkNotNull(executorService));
            return this;
        }

        /**
         *
         * @param toThrow
         * @param <E>
         * @return
         */
        public <E extends Exception> Builder<T> onExceptionThrow(Class<E> toThrow) {
            this.onExceptionThrow = toThrow;
            return this;
        }

        /**
         * @param aggregator
         * @return
         * @throws Exception
         */
        public List<T> aggregate(ThrowableFunction<List<ListenableFuture<T>>, List<T>> aggregator) throws Exception {

            // do we need a default executor (with automatic shutdown)?
            if (!executorService.isPresent()) {
                executorService = Optional.of(DefaultExecutorServiceFactory.ExecutorService);
            }

            ListeningExecutorService listeningExecutorService = listeningDecorator(executorService.get());

            List<ListenableFuture<T>> futures = Lists.newArrayList();
            for (Callable<T> task : tasks) {
                futures.add(listeningExecutorService.submit(task));
            }

            return aggregator.apply(futures);
        }

        /**
         * @return
         * @throws Exception
         */
        public List<T> aggregate() throws Exception {
            return aggregate(new Atomic<T>(onExceptionThrow));
        }

        /**
         *
         * @param function
         * @param <S>
         * @return
         * @throws Exception
         */
        public <S> S aggregate(ThrowableFunction<List<T>, S> function) throws Exception {
            return function.apply(aggregate());
        }
    }

    /**
     * @param <T>
     */
    public static class FilterSuccessful<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {
        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            ListenableFuture<List<T>> listenableFuture = Futures.successfulAsList(input);
            return await(listenableFuture);
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

    public static <T> PipeableFutureTask<T> pipe(ListenableFuture<T> future) {
        checkNotNull(future);
        return new PipeableFutureTask(future);
    }
}
