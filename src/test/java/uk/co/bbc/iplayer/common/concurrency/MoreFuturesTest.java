package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class MoreFuturesTest {

    private static ListeningExecutorService executorService;
    private static Callable<Void> taskToComplete;
    private static Callable<Void> futureThatTakesTooLong;
    private static Callable<Void> taskThrowsACheckedException;
    private static Callable<Void> taskThrowsAnUncheckedException;
    private static Callable<Void> taskToCompleteImmediately;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        taskToComplete = new Callable<Void>() {
            @Override
            public Void call() throws InterruptedException {
                TimeUnit.MILLISECONDS.sleep(50);
                return null;
            }
        };

        taskToCompleteImmediately = new Callable<Void>() {
            @Override
            public Void call() {
                return null;
            }
        };

        futureThatTakesTooLong = new Callable<Void>() {
            @Override
            public Void call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(5);
                return null;
            }
        };

        taskThrowsACheckedException = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                throw new Exception("checked");
            }
        };

        taskThrowsAnUncheckedException = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                throw new RuntimeException("Unchecked");
            }
        };
    }

    @Test
    public void timeoutTaskThatTakesTooLong() throws Exception {

        List<ListenableFuture<Void>> futures = Lists.newArrayList();

        ListenableFuture<Void> future1 = executorService.submit(futureThatTakesTooLong);
        ListenableFuture<Void> future2 = executorService.submit(taskToCompleteImmediately);

        futures.add(future1);
        futures.add(future2);

        List<Void> results = MoreFutures.aggregate(futures, new Duration(TimeUnit.MILLISECONDS, 50));

        assertThat(results.size(), is(1));
        assertThat(future2.get(), equalTo(results.get(0)));
        assertAllFutureAreComplete(futures);
    }

    @Test
    public void taskThatThrowsRuntimeException() throws Exception {

        List<ListenableFuture<Void>> futures = Lists.newArrayList();

        ListenableFuture<Void> future1 = executorService.submit(taskToComplete);
        ListenableFuture<Void> future2 = executorService.submit(taskThrowsACheckedException);
        ListenableFuture<Void> future3 = executorService.submit(taskToComplete);
        ListenableFuture<Void> future4 = executorService.submit(taskToComplete);

        futures.add(future1);
        futures.add(future2);
        futures.add(future3);
        futures.add(future4);

        List<Void> results = MoreFutures.aggregate(futures, new Duration(TimeUnit.MILLISECONDS, 200));

        assertAllFutureAreComplete(futures);
    }

    @Test
    public void aggregateWithCancelledTask() throws MoreFuturesException {

        List<ListenableFuture<Void>> futures = new ArrayList<ListenableFuture<Void>>();

        ListenableFuture<Void> future = executorService.submit(futureThatTakesTooLong);
        future.cancel(true);
        futures.add(future);

        MoreFutures.aggregate(futures, Duration.create());

        assertAllFutureAreComplete(futures);
    }

    @Test
    public void futureIsInterrupted() throws Exception {
        verifyCancelledAndExceptionThrown(new InterruptedException("boom!"));
    }

    @Test
    public void futureHasExecutionException() throws Exception {
        verifyCancelledAndExceptionThrown(new ExecutionException(new RuntimeException("boom!")));
    }

    @Test
    public void futureHasTimeoutException() throws Exception {
        verifyCancelledAndExceptionThrown(new TimeoutException("boom!"));
    }

    @Test
    public void awaitOrThrowWhenCheckedExceptionIsThrown() throws MoreFuturesException {
        thrown.expect(MoreFuturesException.class);
        ListenableFuture<Void> future = executorService.submit(taskThrowsACheckedException);
        MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    //@Test
    public void awaitOrThrowWhenUncheckedExceptionIsThrown() throws MoreFuturesException {
        thrown.expect(MoreFuturesException.class);
        ListenableFuture<Void> future = executorService.submit(taskThrowsAnUncheckedException);
        MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    private void verifyCancelledAndExceptionThrown(Exception exception) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<Void> future = mock(ListenableFuture.class);
        when(future.get(anyLong(), any(TimeUnit.class))).thenThrow(exception);
        MoreFuturesException expected = null;
        try {
            MoreFutures.await(future, Duration.create());
        } catch (Exception ex) {
            expected = (MoreFuturesException) ex;
        }
        assertThat(expected, is(notNullValue()));
        verify(future).cancel(true);
    }

    private void assertAllFutureAreComplete(List<ListenableFuture<Void>> futures) {
        for (ListenableFuture future : futures) {
            assertThat(future.isDone(), is(true));
        }
    }

    @AfterClass
    public static void tearDown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

}
