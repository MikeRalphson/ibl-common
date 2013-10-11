package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: spragn01
 * Date: 11/10/2013
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public class EvenMoreExecutors {

    public static ExecutorService createFixedLoggingExecutorService(int threads, String poolName) {

        ThreadFactory threadFactory = new LoggingThreadFactory(poolName);
        RejectedExecutionHandler abort = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                threads,
                threads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory,
                abort
        );

        return threadPoolExecutor;
    }

    public static ExecutorService createCachedPoolLoggingExecutorService(String poolName) {

        ThreadFactory threadFactory = new LoggingThreadFactory(poolName);
        RejectedExecutionHandler abort = new ThreadPoolExecutor.AbortPolicy();

        int corePoolSize = 0;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                Integer.MAX_VALUE,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory,
                abort
        );

        return threadPoolExecutor;
    }
}