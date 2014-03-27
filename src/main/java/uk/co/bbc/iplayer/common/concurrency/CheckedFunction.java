package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bbc.iplayer.common.utils.ExceptionUtil;

import javax.annotation.Nullable;

public abstract class CheckedFunction<T, V> implements Function<T, V> {
    static Logger LOG = LoggerFactory.getLogger(CheckedCallable.class);
    private String info;

    protected CheckedFunction(String info) {
        this.info = info;
    }

    @Nullable
    @Override
    public final V apply(@Nullable T t) {
        try {
            final V result = checkedApply(t);
            LOG.debug("Success: {}", info);
            return result;
        } catch (RuntimeException e) {
            ExceptionUtil.logExceptionSummary(LOG, info, e);
            throw e;
        }
    }

    public abstract V checkedApply(@Nullable T t);
}
