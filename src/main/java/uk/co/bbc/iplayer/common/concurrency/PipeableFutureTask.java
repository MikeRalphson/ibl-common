package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import javax.annotation.Nonnull;

/**
 * Created by spragn01 on 25/03/2014.
 */
public class PipeableFutureTask<V> extends ForwardingFutureTask<V> implements PipeableFuture<V> {

    /**
     *
     * @param delegate
     * @param <V>
     * @return
     */
    public static <V> PipeableFuture<V> create(ListenableFuture<V> delegate) {
        return new PipeableFutureTask<V>(delegate);
    }

    /**
     *
     * @param delegate
     */
    private PipeableFutureTask(ListenableFuture<V> delegate) {
        super(delegate);
    }

    /**
     *
     * @param throwableFunction
     * @param <O>
     * @return
     */
    @Override
    public <O> PipeableFuture<O> to(@Nonnull final ThrowableFunction<V, O> throwableFunction) {

        return create(
                Futures.transform(getDelegate(), new AsyncFunction<V, O>() {
                    @Override
                    public ListenableFuture<O> apply(V input) throws Exception {
                        return Futures.immediateFuture(throwableFunction.apply(input));
                    }
                })
        );
    }
}
