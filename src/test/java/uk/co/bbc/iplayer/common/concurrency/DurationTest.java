package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DurationTest {

    @Test
    public void verifyDefault() {
        Duration duration = Duration.create();
        assertThat(duration.getLength(), is(5000L));
        assertThat(duration.getTimeUnit(), is(TimeUnit.MILLISECONDS));
    }

    @Test
    public void verifyInMilliSeconds() {
        Duration duration = Duration.inMilliSeconds(50);
        assertThat(duration.getLength(), is(50L));
        assertThat(duration.getTimeUnit(), is(TimeUnit.MILLISECONDS));
    }

    @Test
    public void verifyInSeconds() {
        Duration duration = Duration.inSeconds(50);
        assertThat(duration.getLength(), is(50L));
        assertThat(duration.getTimeUnit(), is(TimeUnit.SECONDS));
    }
}
