package uk.co.bbc.iplayer.common.concurrency;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private String name;

    public NamedThreadFactory(String poolName) {
        this.name = poolName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new NamedThread(runnable, name);
    }

    public String getName() {
        return name;
    }
}
