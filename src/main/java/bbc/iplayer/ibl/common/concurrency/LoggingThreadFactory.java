package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.ThreadFactory;

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
