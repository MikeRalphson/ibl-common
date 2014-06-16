package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.util.concurrent.Futures.immediateFailedFuture;
import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
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
    public static final String CANCELED = "canceled";

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
    public void createFutureChainContainingUsingAdd() throws Exception {

        List<String> aggregation = MoreFutures
                .composeFuturesOf(String.class)
                    .createFuture(createTask("1"))
                    .createFuture(createTask("2"))
                    .createFuture(createTask("3"))
                    .using(executorService)
                .aggregate();

        assertThat(aggregation, hasItems("1", "2", "3"));
    }

    @Test
    public void createFutureChainUsingDefaultExecutorService() throws Exception {

        List<String> aggregation = MoreFutures
                .composeFuturesOf(String.class)
                    .createFuture(createTask("1"))
                    .createFuture(createTask("2"))
                    .createFuture(createTask("3"))
                .aggregate();

        assertThat(aggregation, hasItems("1", "2", "3"));
    }

    @Test
    public void createFutureChainUsingAddAll() throws Exception {

        List<String> aggregation = MoreFutures
                .composeFuturesOf(String.class)
                    .createFutures(newArrayList(createTask("1"), createTask("2"), createTask("3")))
                    .using(executorService)
                .aggregate();

        assertThat(aggregation, hasItems("1", "2", "3"));
    }

    @Test
    public void customAggregationTranformingToADifferentType() throws Exception {

        Integer length = MoreFutures
                .composeFuturesOf(String.class)
                    .createFutures(newArrayList(createTask("I"), createTask("II"), createTask("III")))
                    .using(executorService)
                    .aggregate(new ThrowableFunction<List<String>, Integer>() {
                        @Override
                        public Integer apply(List<String> input) throws Exception {
                            String max = Collections.max(input);
                            return max.length();
                        }
                    });

        assertThat(length, is(3));
    }

    @Test
    public void customAggregationTranformingToAListOfADifferentType() throws Exception {

        List<Integer> integers = MoreFutures
                .composeFuturesOf(String.class)
                    .createFutures(newArrayList(createTask("I"), createTask("II"), createTask("III")))
                    .using(executorService)
                    .aggregate(new ThrowableFunction<List<String>, List<Integer>>() {
                        @Override
                        public List<Integer> apply(List<String> input) throws Exception {
                            List<Integer> integers = Lists.newArrayList();
                            for (String s : input) {
                                integers.add(s.length());
                            }
                            return integers;
                        }
                    });

        assertThat(integers.size(), is(3));

        assertThat(integers.get(0), is(1));
        assertThat(integers.get(1), is(2));
        assertThat(integers.get(2), is(3));
    }

    @Test
    public void composedFutures() throws Exception {

        PipeableFuture<List<String>> pipeableFuture = MoreFutures
                .composeFuturesOf(String.class)
                    .createFutures(newArrayList(createTask("I"), createTask("II"), createTask("III")))
                    .using(executorService)
                .asFuture();

        List<String> strings = pipeableFuture.get();
        assertThat(strings, contains("I", "II", "III"));
    }

    @Test
    public void verifyExceptionConvertionWhenAnExceptionIsThrown() throws Exception {

        expectedException.expect(TestFutureException.class);

        MoreFutures
                .composeFuturesOf(String.class)
                    .createFuture(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            throw new Exception();
                        }
                    })
                    .onExceptionThrow(TestFutureException.class)
                    .using(executorService)
                .aggregate();
    }

    @Test
    public void successfulGetFromPipeableFuture() throws ExecutionException, InterruptedException {

        String sourceInput = "original";

        ListenableFuture<String> sourceFuture = immediateFuture(sourceInput);

        // disallow direct construction
        PipeableFuture<String> pipeableFuture = PipeableFutureTask.create(sourceFuture);

        PipeableFuture<Boolean> product = pipeableFuture
                .to(new ThrowableFunction<String, Boolean>() {
                    @Override
                    public Boolean apply(String input) throws Exception {
                        return input.startsWith("ori");
                    }
                });

        Boolean match = product.get();

        assertTrue(match);
    }

    @Test
    public void createSimpleFuturePipeline() throws ExecutionException, InterruptedException {

        // PipeableFutureTask to allow futures to be composeFuturesOf (using transform)
        ListenableFuture<Boolean> future = immediateFuture(true);

        PipeableFuture<String> pipeableFuture = MoreFutures
                .pipe(future)
                    .to(new ThrowableFunction<Boolean, String>() {
                        @Override
                        public String apply(Boolean input) throws Exception {
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

    @Test
    public void composeExistingFutureUsingAdd() throws Exception {

        ListenableFuture<String> listenableFuture1 = immediateFuture("I");
        ListenableFuture<String> listenableFuture2 = immediateFuture("II");
        ListenableFuture<String> listenableFuture3 = immediateFuture("III");

        List<String> strings = MoreFutures
                .composeFuturesOf(String.class)
                    .addFuture(listenableFuture1)
                    .addFuture(listenableFuture2)
                    .addFuture(listenableFuture3)
                .aggregate();

        assertThat(strings, contains("I", "II", "III"));
    }

    @Test
    public void composeExistingFuturesUsingAdd() throws Exception {

        List<ListenableFuture<String>> futures = Lists.newArrayList();
        futures.add(immediateFuture("I"));
        futures.add(immediateFuture("II"));
        futures.add(immediateFuture("III"));

        List<String> strings = MoreFutures
                .composeFuturesOf(String.class)
                    .addAllFutures(futures)
                .aggregate();

        assertThat(strings, contains("I", "II", "III"));
    }

    @Test
    @Ignore("Not implemented!")
    public void canelFutureOnTimeout() throws Exception {

        ListenableFuture<String> pending = createListenableFuture(createTimedTask(CANCELED, inMilliSeconds(5000)));

        List<String> strings = MoreFutures
                .composeFuturesOf(String.class)
                    .addFuture(pending)
                    // TODO
                    .duration(inMilliSeconds(1))
                .aggregate(new FilterSuccessful<String>());

        assertThat(pending.isDone(), is(true));
        assertThat(strings.size(), is(0));
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

        List<String> results = MoreFutures.aggregate(futures, Duration.inMilliSeconds(50));

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

        List<String> results = MoreFutures.aggregate(futures, Duration.inMilliSeconds(50));

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