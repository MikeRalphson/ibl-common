package uk.co.bbc.iplayer.common.utils;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolConfig.class.getName());

    public static PoolConfig getConfig(ThreadPoolExecutor executor) {
        return new PoolConfig(executor);
    }

    public static PoolConfig getConfig(ExecutorService executorService) {

        Preconditions.checkNotNull(executorService);

        if (executorService instanceof ThreadPoolExecutor) {
            return getConfig((ThreadPoolExecutor) executorService);
        }

        throw new IllegalArgumentException("Expected ThreadPoolExecutor, got " + executorService.getClass().getName());
    }

    public static class PoolConfig {

        private final int corePoolSize;
        private final int poolSize;
        private final int activeCount;
        private final long completedTaskCount;
        private final int larestSize;
        private final int maxSize;
        private final long taskCount;

        public PoolConfig(ThreadPoolExecutor executor) {
            this.corePoolSize = executor.getCorePoolSize();
            this.poolSize = executor.getPoolSize();
            this.activeCount = executor.getActiveCount();
            this.completedTaskCount = executor.getCompletedTaskCount();
            this.larestSize = executor.getLargestPoolSize();
            this.maxSize = executor.getMaximumPoolSize();
            taskCount = executor.getTaskCount();
        }

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public int getPoolSize() {
            return poolSize;
        }

        public int getActiveCount() {
            return activeCount;
        }

        public long getCompletedTaskCount() {
            return completedTaskCount;
        }

        public int getLarestSize() {
            return larestSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        @Override
        public String toString() {
            return "PoolConfig { " +
                    "corePoolSize = " + corePoolSize +
                    ", poolSize = " + poolSize +
                    ", activeCount = " + activeCount +
                    ", scheduledTaskCount = " + taskCount +
                    ", completedTaskCount = " + completedTaskCount +
                    ", largestSize = " + larestSize +
                    ", maxSize = " + maxSize +
                    " }";
        }
    }
}
