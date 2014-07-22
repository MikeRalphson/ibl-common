package uk.co.bbc.iplayer.common.concurrency;

public class MoreFuturesException extends Exception {

    private IdentifyingFuture identifyingFuture;

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

    public MoreFuturesException(String message, Throwable throwable, IdentifyingFuture identifyingFuture) {
        super(message, throwable);
        this.identifyingFuture = identifyingFuture;
    }

    public boolean hasIdentifyingFuture(){
        return identifyingFuture != null;
    }

    public IdentifyingFuture getIdentifyingFuture() {
        return identifyingFuture;
    }
}
