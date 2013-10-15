package bbc.iplayer.ibl.common.concurrency;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

public class LoggingThreadPoolExecutor extends ThreadPoolExecutor {

    private final static Logger LOG = Logger.getLogger(LoggingThreadPoolExecutor.class);

    public LoggingThreadPoolExecutor(int i, int i2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> runnables) {
        super(i, i2, l, timeUnit, runnables);
    }

    public LoggingThreadPoolExecutor(int i, int i2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> runnables, ThreadFactory threadFactory) {
        super(i, i2, l, timeUnit, runnables, threadFactory);
    }

    public LoggingThreadPoolExecutor(int i, int i2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> runnables, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, i2, l, timeUnit, runnables, rejectedExecutionHandler);
    }

    public LoggingThreadPoolExecutor(int i, int i2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> runnables, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, i2, l, timeUnit, runnables, threadFactory, rejectedExecutionHandler);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("About to start: " + thread.getName());

            StringBuilder builder = new StringBuilder();
            if (thread instanceof LoggingThread) {
                builder
                        .append("Threads created: ")
                        .append(LoggingThread.getThreadsCreated())
                        .append(" Threads still alive: ")
                        .append(LoggingThread.getThreadsAlive());

                LOG.debug(builder.toString());
            }
        }
    }
}