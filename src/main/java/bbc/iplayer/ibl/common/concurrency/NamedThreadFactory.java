package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private String poolName;

    public NamedThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new NamedThread(runnable, poolName);
    }
}
