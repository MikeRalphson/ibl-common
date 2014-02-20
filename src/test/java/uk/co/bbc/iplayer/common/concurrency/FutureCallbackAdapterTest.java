package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FutureCallbackAdapterTest {
    private FutureCallbackAdapter<String> unit;

    @Mock
    private Logger mockLogger;
    private String info;

    @Before
    public void setUp() throws Exception {

        info = "Testing 123";
        unit = new FutureCallbackAdapter(info);
        unit.LOG = mockLogger;
    }

    @Test
    public void successShouldLogDebugMessage() throws Exception {
        unit.onSuccess(info);

        verify(mockLogger).debug("Success: {}", info);
        verifyNoMoreInteractions(mockLogger);
    }

    @Test
    public void onFailureShouldLogWarnMessageIfLogLevelIsNotERROR() throws Exception {
        when(mockLogger.isErrorEnabled()).thenReturn(false);
        final RuntimeException throwable = buildExceptionWithMultipleCauses();

        unit.onFailure(throwable);

        verify(mockLogger).isErrorEnabled();
        verify(mockLogger).warn("Failed to get: " + info, throwable);
        verifyNoMoreInteractions(mockLogger);
    }

    @Test
    public void onFailureShouldLogTop10ExeptionCausesWhenLogLevelIsERROR() throws Exception {
        when(mockLogger.isErrorEnabled()).thenReturn(true);

        final RuntimeException throwable = buildExceptionWithMultipleCauses();

        unit.onFailure(throwable);

        String expectedErroLog = "Failed to get: Testing 123" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 14" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 13" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 12" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 11" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 10" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 9" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 8" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 7" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 6" +
                "\nCaused by: class java.lang.RuntimeException - Exception Message 5";

        verify(mockLogger).isErrorEnabled();
        verify(mockLogger).error(expectedErroLog);
        verifyNoMoreInteractions(mockLogger);
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
