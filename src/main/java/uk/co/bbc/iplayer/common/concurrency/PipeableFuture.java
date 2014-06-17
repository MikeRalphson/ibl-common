package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;

public interface PipeableFuture<V> extends ListenableFuture<V> {
    <S> PipeableFuture<S> to(Function<V, S> input);
}
