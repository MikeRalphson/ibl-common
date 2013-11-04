package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.*;

public class EvenMoreExecutors {

    private static final long DEFAULT_KEEP_ALIVE = 60L;

    public static ExecutorService namedFixedExecutorService(int threads, String poolName) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
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

    public static ExecutorService namedCachedExecutorService(String poolName) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
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

    public static ExecutorService boundedNamedCachedExecutorService(int maxThreadBound, String poolName) {
        return boundedNamedCachedExecutorService(maxThreadBound, poolName, DEFAULT_KEEP_ALIVE);
    }

    // Added parameter to allow testing
    public static ExecutorService boundedNamedCachedExecutorService(int maxThreadBound, String poolName, long keepalive) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        RejectedExecutionHandler abort = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                maxThreadBound,
                maxThreadBound,
                keepalive,
                TimeUnit.SECONDS,
                queue,
                threadFactory,
                abort
        );

        threadPoolExecutor.allowCoreThreadTimeOut(true);

        return threadPoolExecutor;
    }

}