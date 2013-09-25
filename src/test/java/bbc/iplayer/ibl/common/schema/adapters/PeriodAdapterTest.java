package bbc.iplayer.ibl.common.schema.adapters;

import org.joda.time.Period;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PeriodAdapterTest {

    private PeriodAdapter periodAdapter = new PeriodAdapter();

    @Test
    public void testUnmarshallsISOFormattedPeriod() throws Exception {
        String marshalledPeriod = "PT30S";
        Period period = periodAdapter.unmarshal(marshalledPeriod);
        assertThat(period.getSeconds(), is(30));
    }

    @Test
    public void testMarshallsPeriodIntoISOFormat() throws Exception {
        Period period = new Period(123000);
        assertThat(periodAdapter.marshal(period), is("PT2M3S"));
    }

    @Test
    public void testIgnoresMilliSecondsWhenMarshalling() throws Exception {
        Period period = new Period(10543);
        assertThat(periodAdapter.marshal(period), is("PT10S"));
    }

}
