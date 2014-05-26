package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bbc.iplayer.common.utils.ExceptionUtil;

import javax.annotation.Nullable;

public abstract class CheckedFunction<T, V> implements Function<T, V> {
    static Logger LOG = LoggerFactory.getLogger(CheckedFunction.class);
    private String taskDescription;

    protected CheckedFunction(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Nullable
    @Override
    public final V apply(@Nullable T t) {
        try {
            final V result = checkedApply(t);
            LOG.debug("Success: {}", taskDescription);
            return result;
        } catch (RuntimeException e) {
            ExceptionUtil.logExceptionSummary(LOG, taskDescription, e);
            throw e;
        }
    }

    public abstract V checkedApply(@Nullable T t);
}
