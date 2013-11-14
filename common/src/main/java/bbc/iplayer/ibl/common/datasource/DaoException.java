package bbc.iplayer.ibl.common.datasource;

public class DaoException extends Exception {
    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
