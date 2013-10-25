package bbc.iplayer.ibl.common.concurrency;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EvenMoreExecutorsTest {

    public static final int THREADS = 10;

    @Test
    public void createCustomFixedExecutor() {

        ExecutorService executorService = EvenMoreExecutors.createFixedNamedThreadExecutorService(THREADS, "IBL");

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        // Fixed pool, so core and max size should be the same
        assertThat(threadPoolExecutor.getCorePoolSize(), is(THREADS));
        assertThat(threadPoolExecutor.getMaximumPoolSize(), is(THREADS));

        NamedThreadFactory threadFactory = (NamedThreadFactory) threadPoolExecutor.getThreadFactory();
        assertThat(threadFactory, instanceOf(NamedThreadFactory.class));
    }

}
