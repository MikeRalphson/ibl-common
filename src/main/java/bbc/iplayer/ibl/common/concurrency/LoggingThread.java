package bbc.iplayer.ibl.common.concurrency;

import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class LoggingThread extends Thread {

    private final static String DEFAULT_THREAD_NAME = "IBL-THREAD";
    private final static Logger LOG = Logger.getLogger(LoggingThread.class);
    private final static AtomicInteger created = new AtomicInteger();
    private final static AtomicInteger alive = new AtomicInteger();

    public LoggingThread(Runnable runnable) {
        super(runnable, DEFAULT_THREAD_NAME);
    }

    public LoggingThread(Runnable runnable, String s) {
        super(runnable, s + ":" + created.incrementAndGet());
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
            LOG.debug("Creating thread: " + getName());
        }

        try {
            alive.incrementAndGet();
            super.run();

        } finally {
            alive.decrementAndGet();

            if (LOG.isDebugEnabled()) {
                LOG.debug(getName() + " thread finished");
            }
        }
    }

    public static AtomicInteger getThreadsCreated() {
        return created;
    }

    public static AtomicInteger getThreadsAlive() {
        return alive;
    }
}