package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by spragn01 on 26/03/2014.
 */
public class ForwardingFutureTask<V> implements ListenableFuture<V> {

    private ListenableFuture<V> delegate;

    public ForwardingFutureTask(ListenableFuture<V> delegate) {
        checkNotNull(delegate, "expected future delegate, got null");
        this.delegate = delegate;
    }

    @Override
    public boolean cancel(boolean b) {
        return delegate.cancel(b);
    }

    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return delegate.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return delegate.get();
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.get(l, timeUnit);
    }

    public ListenableFuture<V> getDelegate() {
        return delegate;
    }

    @Override
    public void addListener(Runnable runnable, Executor executor) {
        delegate.addListener(runnable, executor);
    }
}
