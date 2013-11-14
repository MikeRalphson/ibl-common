package bbc.iplayer.ibl.common.concurrency;

public class MoreFuturesException extends Exception {

    public MoreFuturesException() {
    }

    public MoreFuturesException(String s) {
        super(s);
    }

    public MoreFuturesException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MoreFuturesException(Throwable throwable) {
        super(throwable);
    }
}
