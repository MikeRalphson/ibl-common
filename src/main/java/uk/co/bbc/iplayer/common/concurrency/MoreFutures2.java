package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;

public final class MoreFutures2 {

    public static final Duration DURATION = Duration.create();

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
     * @param <T>
     */
    public static class Builder<T> {

        private Optional<ExecutorService> executorService;
        private ThrowableFunction<ListenableFuture<List<T>>, List<T>> aggregator;
        private List<Callable<T>> tasks = Lists.newArrayList();

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
         * @param aggregator
         * @return
         * @throws Exception
         */
        public List<T> aggregate(ThrowableFunction<List<ListenableFuture<T>>, List<T>> aggregator) throws Exception {

            // build the executor service. Use exiting on default?

            if (!executorService.isPresent()) {
                // assigned default (exiting executor. Create as Singleton)
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
            return aggregate(new FilterSuccessful<T>());
        }
    }

    /**
     *
     * @param <T>
     */
    public static class FilterSuccessful<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {
        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            return Futures.successfulAsList(input).get();
        }
    }

    /**
     *
     * @param <T>
     */
    public static class Atomic<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {
        @Override
        public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
            return Futures.allAsList(input).get();
        }
    }
}
