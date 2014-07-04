package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EvenMoreExecutorsTest {

    public static final int THREADS = 10;
    @Mock
    private RejectedExecutionHandler rejectedExecutionHandler;

    @Test
    public void createCustomFixedExecutor() {

        ExecutorService executorService = EvenMoreExecutors.namedFixedExecutorService(THREADS, "IBL");

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        // Fixed pool, so core and max size should be the same
        assertThat(threadPoolExecutor.getCorePoolSize(), is(THREADS));
        assertThat(threadPoolExecutor.getMaximumPoolSize(), is(THREADS));

        NamedThreadFactory threadFactory = (NamedThreadFactory) threadPoolExecutor.getThreadFactory();
        assertThat(threadFactory, instanceOf(NamedThreadFactory.class));
    }

    @Test
    public void boundedNameCachedTerminatesAfterKeepAlive() throws InterruptedException {
        testBoundedNameCache(100);
        verifyNoMoreInteractions(rejectedExecutionHandler);
    }

    @Test
    public void boundedNameCachedThrowsExceptionIfQueueIsFilled() throws InterruptedException {
        testBoundedNameCache(10);
        verify(rejectedExecutionHandler, atLeastOnce()).rejectedExecution(isA(Runnable.class), isA(ThreadPoolExecutor.class));
    }

    public void testBoundedNameCache(int queueSize) throws InterruptedException {
        long keepalive = 3;
        int nThreads = 5;
        ExecutorService executorService = EvenMoreExecutors.boundedNamedCachedExecutorService(nThreads, "TEST-POOL", keepalive, queueSize, rejectedExecutionHandler);

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    return null;
                }
            });
        }

        TimeUnit.SECONDS.sleep(keepalive + 1);
        int count = NamedThread.getThreadsAlive();
        assertThat(count, is(0));
        executorService.shutdown();
    }

    @Test
    public void boundedNameCachedKeepThreadsAlive() throws InterruptedException {

        int nThreads = 5;

        // keepalive 1 min
        ExecutorService executorService = EvenMoreExecutors.boundedNamedCachedExecutorService(nThreads, "TEST-POOL", 3, 11, rejectedExecutionHandler);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    return null;
                }
            });
        }

        TimeUnit.SECONDS.sleep(2);
        int count = NamedThread.getThreadsAlive();
        assertThat(count, is(nThreads));
        executorService.shutdown();
        verifyNoMoreInteractions(rejectedExecutionHandler);
    }
}
