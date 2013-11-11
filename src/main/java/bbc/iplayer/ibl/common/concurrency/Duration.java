package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.TimeUnit;

public class Duration {
    private final TimeUnit timeUnit;
    private final long length;
    private final static long DEFAULT_MILLISECONDS = 15000;
    private final static TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECONDS;

    public Duration() {
        this(DEFAULT_UNIT, DEFAULT_MILLISECONDS);
    }

    public Duration(TimeUnit timeUnit, long length) {
        this.timeUnit = timeUnit;
        this.length = length;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long getLength() {
        return length;
    }

    // default
    public static Duration create() {
        return new Duration();
    }

    public static Duration inMilliSeconds(int i) {
        return new Duration(TimeUnit.MILLISECONDS, i);
    }
}
