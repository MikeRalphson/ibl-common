package uk.co.bbc.iplayer.common.concurrency;

public class TestFutureException extends Exception {
    public TestFutureException() {
    }

    public TestFutureException(String s) {
        super(s);
    }

    public TestFutureException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TestFutureException(Throwable throwable) {
        super(throwable);
    }
}
