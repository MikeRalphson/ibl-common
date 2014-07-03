package uk.co.bbc.iplayer.common.concurrency;

import java.util.concurrent.*;

public class EvenMoreExecutors {

    private static final long DEFAULT_KEEP_ALIVE = 60L;

    public static ExecutorService namedFixedExecutorService(int nThreads, String poolName) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
        RejectedExecutionHandler abort = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                nThreads,
                nThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory,
                abort
        );

        return threadPoolExecutor;
    }

    public static ExecutorService namedCachedExecutorService(String poolName) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        int corePoolSize = 0;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                Integer.MAX_VALUE,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory,
                abortPolicy
        );

        return threadPoolExecutor;
    }

    public static ExecutorService boundedNamedCachedExecutorService(int maxThreadBound, String poolName, int queueSize, RejectedExecutionHandler abortPolicy) {
        return boundedNamedCachedExecutorService(maxThreadBound, poolName, DEFAULT_KEEP_ALIVE, queueSize, abortPolicy);
    }

    // Added parameter to allow testing
    static ExecutorService boundedNamedCachedExecutorService(int maxThreadBound, String poolName, long keepalive, int queueSize, RejectedExecutionHandler abortPolicy) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(queueSize);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                maxThreadBound,
                maxThreadBound,
                keepalive,
                TimeUnit.SECONDS,
                queue,
                threadFactory,
                abortPolicy
        );

        threadPoolExecutor.allowCoreThreadTimeOut(true);

        return threadPoolExecutor;
    }

}