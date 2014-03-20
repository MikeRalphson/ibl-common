package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExecutorServiceUtilTest {

    private ExecutorService executorService;

    @Before
    public void setup() {
        executorService = Executors.newFixedThreadPool(10);
    }

    @Test
    public void verifyWithNoTasksRunning() throws InterruptedException {
        assertThat(executorService.isShutdown(), is(false));
        ExecutorServiceUtil.shutdownQuietly(executorService);
        assertThat(executorService.isShutdown(), is(true));
    }

    @Test
    public void verifyShutdownWithTasksRunning() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        ExecutorServiceUtil.shutdownQuietly(executorService);

        assertThat(executorService.isShutdown(), is(true));
    }

    @Test
    public void attemptToShutdownExecutorThatIsAlreadyShutdown() throws InterruptedException {
        executorService.shutdownNow();
        ExecutorServiceUtil.shutdownQuietly(executorService);
    }

    @Test
    public void doesntThrowWhenReceivedNull() throws InterruptedException {
        executorService.shutdownNow();
        ExecutorServiceUtil.shutdownQuietly(null);
    }
}
