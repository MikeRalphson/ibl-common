package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;
import javax.annotation.Nonnull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by spragn01 on 25/03/2014.
 */

// TODO: forwardingFutureTask
public class PipeableFutureTask<V> implements PipeableFuture<V> {

    private ListenableFuture<V> delegate;

    public static <V> PipeableFuture<V> create(ListenableFuture<V> delegate) {
        return new PipeableFutureTask<V>(delegate);
    }

    private PipeableFutureTask(ListenableFuture<V> delegate) {
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
    public <O> PipeableFuture<O> to(@Nonnull final ThrowableFunction<V, O> throwableFunction) {

        return create(
                Futures.transform(delegate, new AsyncFunction<V, O>() {
                    @Override
                    public ListenableFuture<O> apply(V input) throws Exception {
                        return Futures.immediateFuture(throwableFunction.apply(input));
                    }
                })
        );
    }
}
