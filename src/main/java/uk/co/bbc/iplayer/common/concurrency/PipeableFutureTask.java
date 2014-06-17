package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nonnull;

public final class PipeableFutureTask<V> extends ForwardingFutureTask<V> implements PipeableFuture<V> {

    public static <V> PipeableFuture<V> create(ListenableFuture<V> delegate) {
        return new PipeableFutureTask<V>(delegate);
    }

    private PipeableFutureTask(ListenableFuture<V> delegate) {
        super(delegate);
    }

    @Override
    public <O> PipeableFuture<O> to(@Nonnull final Function<V, O> function) {
        return create(
                Futures.transform(getDelegate(), new AsyncFunction<V, O>() {
                    @Override
                    public ListenableFuture<O> apply(V input) throws Exception {
                        return Futures.immediateFuture(function.apply(input));
                    }
                })
        );
    }
}
