package bbc.iplayer.ibl.common.file;

public class FileLoadFailureException extends RuntimeException {

    public FileLoadFailureException() {
    }

    public FileLoadFailureException(String s, Exception e) {
        super(s, e);
    }
}
