package uk.co.bbc.iplayer.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionUtilTest {

    @Mock
    private Logger mockLog;

    @Test
    public void logExceptionSummaryShouldLogMessagesOnlyIfLogLevelIsERROR() throws Exception {
        when(mockLog.isWarnEnabled()).thenReturn(false);
        when(mockLog.isErrorEnabled()).thenReturn(true);
        final RuntimeException exception = buildExceptionWithMultipleCauses();

        ExceptionUtil.logExceptionSummary(mockLog, "Testing 123", exception);

        verify(mockLog).error(getExpectedErrorSummary());
        verify(mockLog).isWarnEnabled();
        verify(mockLog).isErrorEnabled();
        verifyNoMoreInteractions(mockLog);
    }

    @Test
    public void logExceptionSummaryShouldLogExceptionIfLogLevelIsWARN() throws Exception {
        when(mockLog.isWarnEnabled()).thenReturn(true);
        when(mockLog.isErrorEnabled()).thenReturn(false);
        final RuntimeException exception = buildExceptionWithMultipleCauses();

        ExceptionUtil.logExceptionSummary(mockLog, "Testing 123", exception);

        verify(mockLog).warn("Thread failed: Testing 123", exception);
        verify(mockLog).isWarnEnabled();
        verifyNoMoreInteractions(mockLog);
    }

    private String getExpectedErrorSummary() {
        return "Thread failed: Testing 123"
                + "\nCaused by: Exception Message 14"
                + "\nCaused by: Exception Message 13"
                + "\nCaused by: Exception Message 12"
                + "\nCaused by: Exception Message 11"
                + "\nCaused by: Exception Message 10"
                + "\nCaused by: Exception Message 9"
                + "\nCaused by: Exception Message 8"
                + "\nCaused by: Exception Message 7"
                + "\nCaused by: Exception Message 6"
                + "\nCaused by: Exception Message 5"
                + "\nCaused by: Exception Message 4"
                + "\nCaused by: Exception Message 3"
                + "\nCaused by: Exception Message 2"
                + "\nCaused by: Exception Message 1"
                + "\nCaused by: Exception Message 0"
                + "\nCaused by: Test exception message";
    }

    private RuntimeException buildExceptionWithMultipleCauses() {
        RuntimeException exception = new RuntimeException("Test exception message");
        for (int i = 0; i < 15; i++) {
            try {
                addCause(exception, i);
            } catch (RuntimeException e) {
                exception = e;
            }
        }
        return exception;
    }

    private void addCause(RuntimeException exception, int count) {
        throw new RuntimeException("Exception Message " + count, exception);
    }
}
