package bbc.iplayer.ibl.common.concurrency;

import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThread extends Thread {

    private final static String DEFAULT_THREAD_NAME = "APPLICATION-THREAD";
    private final static String NAME_DELIMITER = ":";
    private final static Logger LOG = Logger.getLogger(NamedThread.class);
    private final static AtomicInteger created = new AtomicInteger();
    private final static AtomicInteger alive = new AtomicInteger();

    public NamedThread(Runnable runnable, String name) {
        super(runnable, new StringBuilder()
                .append(name).append(NAME_DELIMITER)
                .append(created.incrementAndGet())
                .toString());

        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                LOG.error("uncaught exception in thread: " + thread.getName(), throwable);
            }
        });
    }

    @Override
    public void run() {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Thread started: " + getName());
        }

        try {
            alive.incrementAndGet();
            super.run();

        } finally {
            alive.decrementAndGet();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Thread finished: " + getName());
            }
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static void resetCreatedThreadCount() {
        created.set(0);
    }
}