package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

/**
 * Created by spragn01 on 25/03/2014.
 */
public interface PipeableFuture<V> extends ListenableFuture<V> {
    <S> PipeableFuture<S> to(ThrowableFunction<V, S> input);
}
