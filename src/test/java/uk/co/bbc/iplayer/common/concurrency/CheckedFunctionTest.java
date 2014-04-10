package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckedFunctionTest {
    public static final String INFO = "Function 123";
    private CheckedFunction<Integer, String> unit;
    @Mock
    private Logger mockLog;

    @Before
    public void setup() throws Exception {
        unit.LOG = mockLog;
    }

    @Test
    public void applyShouldLogSuccess() throws Exception {
        unit = new CheckedFunction<Integer, String>(INFO) {
            @Override
            public String checkedApply(@Nullable Integer integer) {
                return integer.toString();
            }
        };

        final String actual = unit.apply(1234);

        assertEquals("1234", actual);
        verify(mockLog).debug("Success: {}", INFO);
        verifyNoMoreInteractions(mockLog);
    }

    @Test
    public void applyShouldLogExceptionWhenLogLevelIsWARN() throws Exception {

        when(mockLog.isWarnEnabled()).thenReturn(true);
        final RuntimeException expectedException = new RuntimeException("Exception 321");
        unit = new CheckedFunction<Integer, String>(INFO) {
            @Override
            public String checkedApply(@Nullable Integer integer) {
                throw expectedException;
            }
        };

        try {
            unit.apply(1234);
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(expectedException, e);
        }

        verify(mockLog).isWarnEnabled();
        verify(mockLog).warn("Thread failed: " +  INFO ,expectedException);
        verifyNoMoreInteractions(mockLog);
    }

    @Test
    public void applyShouldLogExceptionSummaryWhenLogLevelIsERROR() throws Exception {

        when(mockLog.isWarnEnabled()).thenReturn(false);
        when(mockLog.isErrorEnabled()).thenReturn(true);
        final RuntimeException expectedException = new RuntimeException("Exception 321");
        unit = new CheckedFunction<Integer, String>(INFO) {
            @Override
            public String checkedApply(@Nullable Integer integer) {
                throw expectedException;
            }
        };

        try {
            unit.apply(1234);
            fail("should have thrown an exception");
        } catch (RuntimeException e) {
            assertEquals(expectedException, e);
        }

        verify(mockLog).isWarnEnabled();
        verify(mockLog).isErrorEnabled();
        verify(mockLog).error("Thread failed: Function 123\nCaused by: Exception 321");
        verifyNoMoreInteractions(mockLog);
    }
}
