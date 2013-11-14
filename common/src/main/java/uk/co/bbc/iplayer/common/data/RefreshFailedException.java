package uk.co.bbc.iplayer.common.data;

public class RefreshFailedException extends Exception {
    public RefreshFailedException(Exception e) {
        super(e);
    }
}
