package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.google.common.util.concurrent.Futures.addCallback;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.bbc.iplayer.common.concurrency.TaskFactory.createTask;
import static uk.co.bbc.iplayer.common.concurrency.TaskFactory.createThatThrows;

public class OnCompletionCallbackTest {

    private boolean taskCompleted = false;
    private static ListeningExecutorService executorService;

    @BeforeClass
    public static void init() {
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    }

    @AfterClass
    public static void destroy() throws InterruptedException {
        ExecutorServiceUtil.shutdownQuietly(executorService);
    }

    @Test
    public void verifyFlagUpdateOnCompletation() throws ExecutionException, InterruptedException {

        ListenableFuture<String> pending = executorService.submit(createTask("taskCompleted"));

        addCallback(pending, new OnCompletionCallback<String>() {
            @Override
            public void onCompletion() {
                taskCompleted = true;
            }
        });

        pending.get();
        assertThat(taskCompleted, is(true));
    }

    @Test
    public void verifyFlagUpdateOnCompletationWhenExceptionThrown() throws ExecutionException, InterruptedException {

        ListenableFuture<String> pending = executorService.submit(createThatThrows(new Exception()));

        addCallback(pending, new OnCompletionCallback<String>() {
            @Override
            public void onCompletion() {
                taskCompleted = true;
            }
        });

        try {
            pending.get();
        } catch (ExecutionException e) {
            // chill out, don't care for this test
        }
        assertThat(taskCompleted, is(true));
    }
}
