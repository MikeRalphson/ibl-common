package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import uk.co.bbc.iplayer.common.utils.Listenable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.Futures.*;
import static com.google.common.util.concurrent.MoreExecutors.*;

public final class MoreFutures2 {

    public static final Duration DURATION = Duration.create();

    private MoreFutures2() {
        throw new AssertionError();
    }

    public static <T> Builder<T> chain(Class<T> token) {
        return new Builder<T>();
    }

    public static class Builder<T> {

        private Optional<ExecutorService> executorService;
        private List<Callable<T>> tasks = Lists.newArrayList();

        public Builder<T> add(Callable<T> task) {
            checkNotNull(task);
            tasks.add(task);
            return this;
        }

        public Builder<T> addAll(Collection<Callable<T>> collection) {
            checkNotNull(collection);
            tasks.addAll(collection);
            return this;
        }

        public Builder<T> usingExecutorService(ExecutorService executorService) {
            this.executorService = Optional.of(checkNotNull(executorService));
            return this;
        }

        public List<T> aggregate() {

            // build the executor service. Use exiting on default?
            // submit the tasks
            // return the aggregated list of values

            if (!executorService.isPresent()) {
                // assigned default (exiting executor. Create as Singleton)
            }

            List<ListenableFuture<T>> futures = Lists.newArrayList();
            for (Callable<T> task : tasks) {
                ListeningExecutorService listeningExecutorService = listeningDecorator(executorService.get());
                futures.add(listeningExecutorService.submit(task));
            }

            ListenableFuture<List<T>> aggregate = successfulAsList(futures);

            return getUnchecked(aggregate);
        }

    }

}
