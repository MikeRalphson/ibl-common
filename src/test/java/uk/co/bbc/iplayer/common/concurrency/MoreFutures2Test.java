package uk.co.bbc.iplayer.common.concurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public void createDefault() {

        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "done";
            }
        };

        List<String> aggregate = MoreFutures2
                    .chain(String.class)
                        .add(task)
                        .usingExecutorService(executorService)
                .aggregate();

        System.out.println(aggregate);
    }
}
