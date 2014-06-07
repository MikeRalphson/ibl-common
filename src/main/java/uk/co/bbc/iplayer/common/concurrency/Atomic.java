package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.List;

public class Atomic<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {

    private Class<? extends Exception> toThrow = MoreFuturesException.class;

    public Atomic(Class<? extends Exception> toThrow) {
        this.toThrow = toThrow;
    }

    @Override
    public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
        ListenableFuture<List<T>> listenableFuture = Futures.allAsList(input);
        return MoreFutures.awaitOrThrow(listenableFuture, toThrow);
    }
}
