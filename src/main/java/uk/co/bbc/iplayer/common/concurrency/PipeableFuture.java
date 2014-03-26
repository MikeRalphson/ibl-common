package uk.co.bbc.iplayer.common.concurrency;

import uk.co.bbc.iplayer.common.functions.ThrowableFunction;
import java.util.concurrent.Future;

/**
 * Created by spragn01 on 25/03/2014.
 */
public interface PipeableFuture<V> extends Future<V> {
    <T, S> PipeableFuture<S> to(ThrowableFunction<? super T, ? super S> input);
}
