package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.*;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class _MoreFuturesTest {

    private static ListeningExecutorService executorService;
    private static Callable<String> taskToComplete;
    private static Callable<String> futureThatTakesTooLong;
    private static Callable<String> taskThrowsARuntimeException;
    private static Callable<String> taskThrowsCheckedException;
    private static Callable<String> taskToCompleteImmediately;
    private static Callable<String> futureThatReturnsNull;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {

        taskToComplete = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                TimeUnit.MILLISECONDS.sleep(50);
                return "complete";
            }
        };

        taskToCompleteImmediately = new Callable<String>() {
            @Override
            public String call() {
                return "completeImmediatly";
            }
        };

        futureThatTakesTooLong = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(5);
                return "takesTooLong";
            }
        };

        futureThatReturnsNull = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                return null;
            }
        };

        taskThrowsARuntimeException = new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new RuntimeException("unchecked");
            }
        };

        taskThrowsCheckedException = new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new Exception("checked");
            }
        };
    }

    @Before
    public void setup() {
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    @After
    public void tearDown() throws InterruptedException {
        ExecutorServiceUtil.shutdownQuietly(executorService);
    }

    @Test
    public void timeoutTaskThatTakesTooLong() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        ListenableFuture<String> future1 = executorService.submit(futureThatTakesTooLong);
        ListenableFuture<String> future2 = executorService.submit(taskToCompleteImmediately);

        futures.add(future1);
        futures.add(future2);

        List<String> results = _MoreFutures.aggregate(futures, new Duration(TimeUnit.MILLISECONDS, 50));

        assertThat(results.size(), is(1));
        assertThat(future2.get(), equalTo(results.get(0)));
        assertAllFutureAreComplete(futures);
    }

    @Test
    public void removeNullValues() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        ListenableFuture<String> future1 = executorService.submit(futureThatReturnsNull);
        ListenableFuture<String> future2 = executorService.submit(taskToCompleteImmediately);

        futures.add(future1);
        futures.add(future2);

        List<String> results = _MoreFutures.aggregate(futures, new Duration(TimeUnit.MILLISECONDS, 50));

        assertThat(results.size(), is(1));
        assertThat(future2.get(), equalTo(results.get(0)));
        assertAllFutureAreComplete(futures);
    }

    @Test
    public void taskThatThrowsRuntimeException() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        ListenableFuture<String> future1 = executorService.submit(taskToComplete);
        ListenableFuture<String> future2 = executorService.submit(taskThrowsARuntimeException);
        ListenableFuture<String> future3 = executorService.submit(taskToComplete);
        ListenableFuture<String> future4 = executorService.submit(taskToComplete);

        futures.add(future1);
        futures.add(future2);
        futures.add(future3);
        futures.add(future4);

        List<String> results = _MoreFutures.aggregate(futures, new Duration(TimeUnit.MILLISECONDS, 200));

        assertAllFutureAreComplete(futures);
        assertThat(results.size(), is(3));
    }

    @Test
    public void aggregateWithCancelledTask() throws MoreFuturesException {

        List<ListenableFuture<String>> futures = new ArrayList<ListenableFuture<String>>();

        ListenableFuture<String> future = executorService.submit(futureThatTakesTooLong);
        future.cancel(true);
        futures.add(future);

        _MoreFutures.aggregate(futures, Duration.create());

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
        ListenableFuture<String> future = executorService.submit(taskThrowsCheckedException);
        _MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    @Test
    public void awaitOrThrowWhenUncheckedExceptionIsThrown() throws MoreFuturesException {
        thrown.expect(MoreFuturesException.class);
        ListenableFuture<String> future = executorService.submit(taskThrowsARuntimeException);
        _MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    private void verifyCancelledAndExceptionThrown(Exception exception) throws InterruptedException, ExecutionException, TimeoutException {
        ListenableFuture<String> future = mock(ListenableFuture.class);
        when(future.get(anyLong(), any(TimeUnit.class))).thenThrow(exception);
        MoreFuturesException expected = null;
        try {
            _MoreFutures.await(future, Duration.create());
        } catch (Exception ex) {
            expected = (MoreFuturesException) ex;
        }
        assertThat(expected, is(notNullValue()));
        verify(future).cancel(true);
    }

    private void assertAllFutureAreComplete(List<ListenableFuture<String>> futures) {
        for (ListenableFuture future : futures) {
            assertThat(future.isDone(), is(true));
        }
    }

}