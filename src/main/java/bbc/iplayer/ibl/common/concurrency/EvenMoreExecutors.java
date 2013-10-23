package bbc.iplayer.ibl.common.concurrency;

import java.util.concurrent.*;

public class EvenMoreExecutors {

    public static ExecutorService createFixedNamedThreadExecutorService(int threads, String poolName) {

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
}