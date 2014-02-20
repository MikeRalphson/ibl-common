package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureCallbackAdapter<T> implements FutureCallback<T> {
    static Logger LOG = LoggerFactory.getLogger(FutureCallbackAdapter.class);
    private static final int STACK_TRACE_DEPTH = 10;
    private static final String FAILED_TO_GET = "Failed to get: ";
    private static final String CAUSED_BY = "\nCaused by: ";
    private static final String DASH = " - ";

    private final String info;

    public FutureCallbackAdapter(String info) {
        this.info = info;
    }

    @Override
    public void onSuccess(T obj) {
        LOG.debug("Success: {}", info);
    }

    @Override
    public void onFailure(Throwable throwable) {
        final StringBuilder sb = new StringBuilder(FAILED_TO_GET).append(info);

        if (LOG.isErrorEnabled()) {
            appendCauseMessage(sb, throwable, 0);
            LOG.error(sb.toString());
        }
        else {
            LOG.warn(sb.toString(), throwable);
        }
    }

    private void appendCauseMessage(StringBuilder sb, Throwable throwable, int count) {
        if (count < STACK_TRACE_DEPTH && throwable != null) {
            sb.append(CAUSED_BY).append(throwable.getClass()).append(DASH).append(throwable.getMessage());
            appendCauseMessage(sb, throwable.getCause(), count + 1);
        }
    }



}
