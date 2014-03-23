package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoreFutures2Test {

    private ExecutorService executorService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @After
    public void teardown() throws InterruptedException {
        ExecutorServiceUtil.shutdownQuietly(executorService);
    }

    @Test
    public void createFutureChainContainingUsingAdd() throws Exception {

        List<String> aggregation = MoreFutures2
                .chain(String.class)
                .add(createTask("1"))
                .add(createTask("2"))
                .add(createTask("3"))
                .usingExecutorService(executorService)
                .aggregate();

        assertThat(aggregation, hasItems("1", "2", "3"));
    }

    @Test
    public void createFutureChainUsingAddAll() throws Exception {

        List<String> aggregation = MoreFutures2
                .chain(String.class)
                .addAll(newArrayList(createTask("1"), createTask("2"), createTask("3")))
                .usingExecutorService(executorService)
                .aggregate();

        assertThat(aggregation, hasItems("1", "2", "3"));
    }

    @Test
    public void awaitOnFutureWithinDefaultDuration() throws MoreFuturesException {
        String expectedResult = "1";
        int sleepTimeInSeconds = 1;
        String value = MoreFutures2.await(
                createListenableFuture(createTimedTask(expectedResult, sleepTimeInSeconds)));

        assertThat(value, is(expectedResult));
    }

    @Test
    public void awaitOnFutureExceedingDefaultDuration() throws MoreFuturesException {
        String expectedResult = "returnValue";
        int sleepTimeInMilliSeconds = 8000;

        ListenableFuture<String> listenableFuture = createListenableFuture(createTimedTask(expectedResult, sleepTimeInMilliSeconds));

        // using try as need to make further assertions
        boolean thrown = false;
        try {
            MoreFutures2.await(listenableFuture);
        } catch (MoreFuturesException e) {
            thrown = true;
        }

        assertThat(thrown, is(true));
        assertThat(listenableFuture.isDone(), is(true));
    }

    private Callable<String> createTask(final String returnStr) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return returnStr;
            }
        };
    }

    private Callable<String> createTimedTask(final String returnStr, final int seconds) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(seconds);
                return createTask(returnStr).call();
            }
        };
    }

    private <T> ListenableFuture<T> createListenableFuture(Callable<T> task) {
       return MoreExecutors.listeningDecorator(executorService).submit(task);
    }
}