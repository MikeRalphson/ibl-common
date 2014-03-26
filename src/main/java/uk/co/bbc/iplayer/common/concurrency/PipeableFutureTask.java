package uk.co.bbc.iplayer.common.concurrency;

import uk.co.bbc.iplayer.common.functions.ThrowableFunction;
import java.util.concurrent.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by spragn01 on 25/03/2014.
 */

// TODO: forwardingFutureTask
public class PipeableFutureTask<V> implements PipeableFuture<V> {

    private Future<V> delegate;

    public PipeableFutureTask(Future<V> delegate) {
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

    @Override
    public <T, S> PipeableFuture<S> to(ThrowableFunction<? super T, ? super S> input) {
        return null;
    }
}
