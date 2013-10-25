package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.*;

public class EvenMoreExecutors {

    public static ExecutorService createFixedNamedThreadExecutorService(int threads, String poolName) {

        ThreadFactory threadFactory = new NamedThreadFactory(poolName);
        RejectedExecutionHandler abort = new ThreadPoolExecutor.AbortPolicy();
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                threads,
                threads,
                0L,
                TimeUnit.MILLISECONDS,
                taskQueue,
                threadFactory,
                abort
        );

        return threadPoolExecutor;
    }
}