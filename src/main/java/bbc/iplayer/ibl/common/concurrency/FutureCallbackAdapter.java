package bbc.iplayer.ibl.common.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureCallbackAdapter<T> implements FutureCallback<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FutureCallbackAdapter.class);
    private final String info;

    public FutureCallbackAdapter(String info){
        this.info = info;
    }



    @Override
    public void onSuccess(T obj) {
        LOG.debug("Success:{}",info);
    }

    @Override
    public void onFailure(Throwable throwable) {
        LOG.error("Failed to get: "+info, throwable);
    }
}
