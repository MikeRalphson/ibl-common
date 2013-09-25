package bbc.iplayer.nitro.model.adapters;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException() {
    }

    public InvalidDateFormatException(String s) {
        super(s);
    }

    public InvalidDateFormatException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidDateFormatException(Throwable throwable) {
        super(throwable);
    }
}
