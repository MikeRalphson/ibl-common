package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MoreFutures3Test {

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
        MoreFutures3.FuturePiplineBuilder.createPipeline()
                .doAMethod().build();
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