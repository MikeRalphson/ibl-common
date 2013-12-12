package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NamedThreadTest {

    private static final String CUSTOM_NAME = "custom";
    private static final int N_THREADS_CREATE = 20;
    private static final int N_THREADS_KEEP_ALIVE = 1;
    private List<Thread> threads;
    private CountDownLatch createAndComplete;
    private CountDownLatch createAndKeepAlive;
    private CountDownLatch aliveStart;

    @Before
    public void init() {

        NamedThread.resetCreatedThreadCount();

        createAndComplete = new CountDownLatch(N_THREADS_CREATE);
        createAndKeepAlive = new CountDownLatch(N_THREADS_KEEP_ALIVE);
        aliveStart = new CountDownLatch(N_THREADS_KEEP_ALIVE);

        threads = Lists.newArrayList();

        // create threads
        for (int i = 0; i < N_THREADS_CREATE; i++) {
            threads.add(new NamedThread(new CreateAndComplete(), CUSTOM_NAME));
        }
    }

    @Test
    public void verifyCustomThreadName() {
        Thread first = threads.get(0);
        assertThat(first.getName(), is(CUSTOM_NAME + ":" + 1));
    }

    @Test
    public void verifyThreadCreatedCount() {
        assertThat(NamedThread.getThreadsCreated(), is(N_THREADS_CREATE));
    }

    @Test
    public void verifyThreadsAlive() throws InterruptedException {
        KeepAlive keepAlive = new KeepAlive();
        Thread alive = new NamedThread(keepAlive, CUSTOM_NAME);

        threads.add(alive);
        // start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // wait until 20 threads are done and keepAlive task has actually started
        createAndComplete.await();
        aliveStart.await();

        // should only have 1 thread running
        assertThat(NamedThread.getThreadsAlive(), is(1));

        // signal task to stop, causing thread to terminate
        keepAlive.stop();

        // await actual createAndKeepAlive
        createAndKeepAlive.await();

        // now expecting createAndKeepAlive count to be zero
        assertThat(NamedThread.getThreadsAlive(), is(0));

    }

    private class CreateAndComplete implements Runnable {
        @Override
        public void run() {
            createAndComplete.countDown();
        }
    }

    private class KeepAlive implements Runnable {

        private volatile boolean run = true;

        @Override
        public void run() {
            try {
                aliveStart.countDown();
                while (run) {
                    TimeUnit.MILLISECONDS.sleep(500);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                createAndKeepAlive.countDown();
            }
        }

        public void stop() {
            this.run = false;
        }
    }


    @Test
    public void resetThreadCount() {
        NamedThread.resetCreatedThreadCount();
        assertThat(NamedThread.getThreadsCreated(), is(0));
    }

}
