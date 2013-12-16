package uk.co.bbc.iplayer.common.file;

public class FileLoadFailureException extends RuntimeException {

    public FileLoadFailureException() {
    }

    public FileLoadFailureException(String s, Exception e) {
        super(s, e);
    }
}
