package uk.co.bbc.iplayer.common.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceUtil {

    public static final int DEFAULT_AWAIT = 5;

    private ExecutorServiceUtil() {
        throw new AssertionError();
    }

    public static void shutdownQuietly(ExecutorService executorService) throws InterruptedException {
        shutdownQuietly(executorService, DEFAULT_AWAIT);
    }

    public static void shutdownQuietly(ExecutorService executorService, int await) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                // try to allow any test tasks to finish
                if (!executorService.awaitTermination(await, TimeUnit.SECONDS)) {
                    // some tasks still running. Try again more forcefully
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                // ignore interrupt but attempt (again) to shutdown if current thread interrupted
                executorService.shutdownNow();
            }
        }
    }
}
