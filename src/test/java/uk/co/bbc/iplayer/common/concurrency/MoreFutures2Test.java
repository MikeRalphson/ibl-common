package uk.co.bbc.iplayer.common.concurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class MoreFutures2Test {

    private ExecutorService executorService;

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

    private Callable<String> createTask(final String returnStr) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return returnStr;
            }
        };
    }
}