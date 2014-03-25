package uk.co.bbc.iplayer.common.concurrency;

import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by spragn01 on 25/03/2014.
 */
public class PipeableFutureTask<V> extends FutureTask<V> implements PipeableFuture<V> {

    public PipeableFutureTask(Callable<V> vCallable, Future future) {
        super(vCallable);
    }

    @Override
    public <T> PipeableFuture<V> to(ThrowableFunction<T, V> input) {
        return null;
    }
}
