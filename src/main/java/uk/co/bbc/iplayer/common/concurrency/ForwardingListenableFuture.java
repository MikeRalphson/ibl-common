package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ForwardingListenableFuture<V> implements ListenableFuture<V> {

    private ListenableFuture<V> delegate;

    public ForwardingListenableFuture(ListenableFuture<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void addListener(Runnable runnable, Executor executor) {
        delegate.addListener(runnable, executor);
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
}
