package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.timgroup.statsd.StatsDClient;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.google.common.util.concurrent.Futures.immediateFailedFuture;
import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static uk.co.bbc.iplayer.common.concurrency.Duration.inMilliSeconds;
import static uk.co.bbc.iplayer.common.concurrency.TaskFactory.*;

public class MoreFuturesTest {

    // task return values
    public static final String COMPLETED = "completed";
    public static final String TOO_LONG = "tooLong";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static ListeningExecutorService executorService;

    @Before
    public void setup() {
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
    }

    @After
    public void teardown() throws InterruptedException {
        ExecutorServiceUtil.shutdownQuietly(executorService);
    }

    @Test
    public void successfulGetFromPipeableFuture() throws ExecutionException, InterruptedException {

        String sourceInput = "original";

        ListenableFuture<String> sourceFuture = immediateFuture(sourceInput);

        // disallow direct construction
        PipeableFuture<String> pipeableFuture = PipeableFutureTask.create(sourceFuture);

        PipeableFuture<Boolean> product = pipeableFuture
                .to(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String input) {
                        return input.startsWith("ori");
                    }
                });

        Boolean match = product.get();

        assertTrue(match);
    }

    @Test
    public void createSimpleFuturePipeline() throws ExecutionException, InterruptedException {

        ListenableFuture<Boolean> future = immediateFuture(true);

        PipeableFuture<String> pipeableFuture = MoreFutures
                .pipe(future)
                    .to(new Function<Boolean, String>() {
                        @Override
                        public String apply(Boolean input) {
                            return input.toString();
                        }
                    });

        String result = pipeableFuture.get();

        assertThat(result, is("true"));
    }

    @Test
    public void createFuturePipelineWithMultipleTransformations() throws ExecutionException, InterruptedException {

        String input = "start";

        ListenableFuture<String> future = immediateFuture(input);

        PipeableFuture<Map<String, Integer>> pipeableFuture = MoreFutures
                .pipe(future)
                    .to(new StringToASCII())
                    .to(new ASCIIToString())
                    .to(new CreateStringLengthMap());

        Map<String, Integer> stringIntegerMap = pipeableFuture.get();

        assertThat(stringIntegerMap.size(), is(1));
        assertThat(stringIntegerMap.get(input), is(input.length()));
    }

    /**
     * For backward compatibility - supporting MoreFutures 1.x methods (await and aggregate)
     */
    @Test
    public void timeoutTaskThatTakesTooLong() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        String expectedResult = "completed";

        ListenableFuture<String> pending1 = createListenableFuture(createTimedTask(expectedResult, inMilliSeconds(5000)));
        ListenableFuture<String> pending2 = createListenableFuture(createTimedTask(expectedResult, inMilliSeconds(0)));

        futures.add(pending1);
        futures.add(pending2);

        List<String> results = MoreFutures.aggregate(futures, inMilliSeconds(50));

        assertThat(results.size(), is(1));
        assertThat(pending2.get(), equalTo(results.get(0)));
        assertAllFutureAreComplete(futures);
    }

    @Test
    public void removeNullValues() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        ListenableFuture<String> pending1 = createListenableFuture(createTask(null));
        ListenableFuture<String> pending2 = createListenableFuture(createTimedTask("completeImmediatly", inMilliSeconds(0)));

        futures.add(pending1);
        futures.add(pending2);

        List<String> results = MoreFutures.aggregate(futures, inMilliSeconds(50));

        MatcherAssert.assertThat(results.size(), Matchers.is(1));
        MatcherAssert.assertThat(pending2.get(), equalTo(results.get(0)));
        assertAllFutureAreComplete(futures);
    }

    @Test
    public void taskThatThrowsRuntimeException() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();

        ListenableFuture<String> pending1 = createListenableFuture(createTimedTask(COMPLETED, inMilliSeconds(50)));
        ListenableFuture<String> pending2 = createListenableFuture(createThatThrows(new Exception()));
        ListenableFuture<String> pending3 = createListenableFuture(createTimedTask(COMPLETED, inMilliSeconds(50)));;
        ListenableFuture<String> pending4 = createListenableFuture(createTimedTask(COMPLETED, inMilliSeconds(50)));;

        futures.add(pending1);
        futures.add(pending2);
        futures.add(pending3);
        futures.add(pending4);

        List<String> results = MoreFutures.aggregate(futures, inMilliSeconds(200));

        assertAllFutureAreComplete(futures);
        assertThat(results.size(), is(3));
    }

    @Test
    public void aggregateWithCancelledTask() throws MoreFuturesException {

        List<ListenableFuture<String>> futures = new ArrayList<ListenableFuture<String>>();

        ListenableFuture<String> future = createListenableFuture(createTimedTask(TOO_LONG, inMilliSeconds(5000)));
        future.cancel(true);
        futures.add(future);

        MoreFutures.aggregate(futures, Duration.create());

        assertAllFutureAreComplete(futures);
    }

    @Test
    public void awaitOnFutureWithinDefaultDuration() throws MoreFuturesException {
        String expectedResult = "1";
        String value = MoreFutures.await(
                createListenableFuture(createTimedTask(expectedResult, inMilliSeconds(1))));

        assertThat(value, is(expectedResult));
    }

    @Test
    public void awaitAndCheckedExceptionThrown() throws MoreFuturesException {
        expectedException.expect(MoreFuturesException.class);

        ListenableFuture<String> future = immediateFailedFuture(new Exception());
        MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    @Test
    public void awaitAndUnCheckedExceptionThrown() throws MoreFuturesException {
        expectedException.expect(MoreFuturesException.class);

        ListenableFuture<String> future = immediateFailedFuture(new RuntimeException());
        MoreFutures.awaitOrThrow(future, MoreFuturesException.class);
    }

    @Test
    public void awaitOnFutureExceedingDefaultDuration() throws MoreFuturesException {

        ListenableFuture<String> listenableFuture =
                createListenableFuture(createTimedTask(TOO_LONG, inMilliSeconds(8000)));

        // using try as need to make further assertions
        boolean thrown = false;
        try {
            MoreFutures.await(listenableFuture);
        } catch (MoreFuturesException e) {
            thrown = true;
        }

        assertThat(thrown, is(true));
        assertThat(listenableFuture.isDone(), is(true));
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
    public void awaitOrThrowWhenUncheckedExceptionIsThrown() throws MoreFuturesException {
        expectedException.expect(MoreFuturesException.class);
        MoreFutures.awaitOrThrow(Futures.immediateFailedFuture(new RuntimeException()), MoreFuturesException.class);
    }

    @Test
    public void shouldKeepDescription_WhenIdentifyingFutureTransformed() throws Exception {
        ListenableFuture<String> delegate = immediateFuture(" listenable future ");
        IdentifyingFuture<String> originalFuture = new IdentifyingFuture<String>(delegate, "original description", mock(StatsDClient.class), "statsDescriptor");

        Function<String, String> function = new CheckedFunction<String, String>("trimming function") {
            @Override
            public String checkedApply(String s) {
                return s.trim();
            }
        };

        ListenableFuture<String> transformedFuture = MoreFutures.transformIdentifying(originalFuture, function);

        assertThat(((IdentifyingFuture)transformedFuture).getDescriptor(), is(originalFuture.getDescriptor()));
        assertThat(transformedFuture.get(), is("listenable future"));
    }

    /**
     * Utility methods
     */
    private <T> ListenableFuture<T> createListenableFuture(Callable<T> task) {
        return MoreExecutors.listeningDecorator(executorService).submit(task);
    }

    private void verifyCancelledAndExceptionThrown(Exception exception) throws InterruptedException, ExecutionException, TimeoutException {

        ListenableFuture<String> pending = mock(ListenableFuture.class);

        when(pending.get(anyLong(), any(TimeUnit.class)))
                .thenThrow(exception);

        ListenableFuture<String> future = mock(ListenableFuture.class);

        when(future.get(anyLong(), any(TimeUnit.class)))
                .thenThrow(exception);

        MoreFuturesException expected = null;
        try {
            MoreFutures.await(pending);
        } catch (Exception ex) {
            expected = (MoreFuturesException) ex;
        }

        assertThat(expected, Matchers.is(notNullValue()));
        verify(pending).cancel(true);
    }

    private void assertAllFutureAreComplete(List<ListenableFuture<String>> futures) {
        for (ListenableFuture future : futures) {
            MatcherAssert.assertThat(future.isDone(), Matchers.is(true));
        }
    }
}