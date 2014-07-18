package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.FutureCallback;

public abstract class OnCompletionCallback<V> implements FutureCallback<V> {

    @Override
    final public void onSuccess(V v) {
        onCompletion();
    }

    @Override
    final public void onFailure(Throwable throwable) {
        onCompletion();
    }

    abstract public void onCompletion();
}
