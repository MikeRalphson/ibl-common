package uk.co.bbc.iplayer.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bbc.iplayer.common.concurrency.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class ThreadPoolConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolConfig.class.getName());

    public static PoolConfig getConfig(ThreadPoolExecutor executor) {
        return new PoolConfig(executor);
    }

    public static PoolConfig getConfig(ExecutorService executorService) {

        checkNotNull(executorService);

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
        private final int workQueueSize;
        private String poolName;

        public PoolConfig(ThreadPoolExecutor executor) {
            this.corePoolSize = executor.getCorePoolSize();
            this.poolSize = executor.getPoolSize();
            this.activeCount = executor.getActiveCount();
            this.completedTaskCount = executor.getCompletedTaskCount();
            this.larestSize = executor.getLargestPoolSize();
            this.maxSize = executor.getMaximumPoolSize();
            this.taskCount = executor.getTaskCount();
            this.workQueueSize = executor.getQueue().size();
            this.poolName = executor.toString();

            ThreadFactory threadFactory = executor.getThreadFactory();
            if (threadFactory instanceof NamedThreadFactory) {
                this.poolName = ((NamedThreadFactory) threadFactory).getName();
            }
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

        public int getWorkQueueSize() {
            return workQueueSize;
        }

        public long getTaskCount() {
            return taskCount;
        }

        public String getPoolName() {
            return poolName;
        }

        @Override
        public String toString() {
            return "PoolConfig{" +
                    "corePoolSize=" + corePoolSize +
                    ", poolSize=" + poolSize +
                    ", activeCount=" + activeCount +
                    ", completedTaskCount=" + completedTaskCount +
                    ", larestSize=" + larestSize +
                    ", maxSize=" + maxSize +
                    ", taskCount=" + taskCount +
                    ", workQueueSize=" + workQueueSize +
                    '}';
        }
    }
}
