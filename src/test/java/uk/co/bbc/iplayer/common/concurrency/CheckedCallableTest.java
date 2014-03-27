package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckedCallableTest {

    public static final String INFO = "Testing 123";
    CheckedCallable<String> unit;
    private boolean onSuccessCalled = false;
    private boolean onFailureCalled = false;
    @Mock
    private Logger mockLog;

    @Before
    public void setUp() throws Exception {
        unit.LOG = mockLog;
    }

    @Test
    public void checkedCallShouldCallCallablesCallAndOnSuccessAndLog() throws Exception {
        unit = new CheckedCallable<String>(INFO) {
            @Override
            public String checkedCall() throws Exception {
                return "Body succeeded";
            }

            @Override
            public void onSuccess() {
                onSuccessCalled = true;
            }

            @Override
            public void onFailure() {
                onFailureCalled = true;
            }
        };

        final String actual = unit.call();
        assertEquals("Body succeeded", actual);
        assertTrue(onSuccessCalled);
        assertFalse(onFailureCalled);
        verify(mockLog).debug("Success: {}", INFO);
        verifyNoMoreInteractions(mockLog);
    }

    @Test
    public void checkedCallShouldLogExceptionSummaryAndCallOnFailureWhenLogLevelIsWARN() throws Exception {
        when(mockLog.isWarnEnabled()).thenReturn(true);

        final RuntimeException exception = testFailure();
        verify(mockLog).warn("Thread failed: " + INFO, exception);
        verify(mockLog).isWarnEnabled();
        verifyNoMoreInteractions(mockLog);
    }

    @Test
    public void checkedCallShouldLogExceptionSummaryAndCallOnFailureWhenLogLevelIsDEBUG() throws Exception {
        when(mockLog.isWarnEnabled()).thenReturn(false);
        when(mockLog.isErrorEnabled()).thenReturn(true);

        testFailure();
        verify(mockLog).error("Thread failed: Testing 123\nCaused by: Exception 123");
        verify(mockLog).isWarnEnabled();
        verify(mockLog).isErrorEnabled();
        verifyNoMoreInteractions(mockLog);
    }

    private RuntimeException testFailure() throws Exception {

        final RuntimeException exception = new RuntimeException("Exception 123");

        unit = new CheckedCallable<String>(INFO) {
            @Override
            public String checkedCall() throws Exception {
                throw exception;
            }

            @Override
            public void onSuccess() {
                onSuccessCalled = true;
            }

            @Override
            public void onFailure() {
                onFailureCalled = true;
            }
        };

        try {
            unit.call();
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(exception, e);
        }

        assertFalse(onSuccessCalled);
        assertTrue(onFailureCalled);
        return exception;
    }


}
