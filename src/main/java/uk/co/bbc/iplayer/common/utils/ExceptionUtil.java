package uk.co.bbc.iplayer.common.utils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

public class ExceptionUtil {
    private static final String THREAD_FAILED = "Thread failed: ";
    private static final String CAUSED_BY = "\nCaused by: ";


    public static void logExceptionSummary(Logger logger, String info, Throwable throwable) {
        final StringBuilder sb = new StringBuilder(THREAD_FAILED).append(info);

        if (logger.isWarnEnabled()) {
            logger.warn(sb.toString(), throwable);
        }
        else if (logger.isErrorEnabled()) {

            final Throwable[] rootCauseStackTrace = ExceptionUtils.getThrowables(throwable);
            for (Throwable cause : rootCauseStackTrace) {
                sb.append(CAUSED_BY).append(cause.getMessage());
            }

            logger.error(sb.toString());
        }
    }
}
