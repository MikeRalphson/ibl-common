package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * User: spragn01
 * Date: 11/10/2013
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class LoggingThreadFactory implements ThreadFactory {

    private String poolName;

    public LoggingThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new LoggingThread(runnable, poolName);
    }
}
