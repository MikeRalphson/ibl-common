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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.bbc.iplayer.common.concurrency.Duration.*;

public class MoreFutures2Test {

    private static Callable<String> taskToComplete;
    private static Callable<String> taskThatTakesTooLong;
    private static Callable<String> taskThrowsARuntimeException;
    private static Callable<String> taskThrowsCheckedException;
    private static Callable<String> taskToCompleteImmediately;
    private static Callable<String> taskThatReturnsNull;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static ListeningExecutorService executorService;

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

        taskThatTakesTooLong = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(5);
                return "takesTooLong";
            }
        };

        taskThatReturnsNull = new Callable<String>() {
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
                    .aggregateAndTransform(new ThrowableFunction<List<String>, Integer>() {
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
                    .aggregateAndTransform(new ThrowableFunction<List<String>, List<Integer>>() {
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
    public void canelFutureOnTimeout() throws Exception {

        ListenableFuture<String> pending = executorService.submit(taskThatTakesTooLong);

        List<String> strings = MoreFutures
                .composeFuturesOf(String.class)
                    .addFuture(pending)
                    .duration(inMilliSeconds(1))
                .aggregateAndTransform(new MoreFutures.FilterSuccessful<String>());

        assertThat(pending.isDone(), is(true));
        assertThat(strings.size(), is(0));
    }

    @Test
    public void awaitOnFutureWithinDefaultDuration() throws MoreFuturesException {
        String expectedResult = "1";
        int sleepTimeInSeconds = 1;
        String value = MoreFutures.await(
                createListenableFuture(createTimedTask(expectedResult, sleepTimeInSeconds)));

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
        String expectedResult = "returnValue";
        int sleepTimeInMilliSeconds = 8000;

        ListenableFuture<String> listenableFuture = createListenableFuture(createTimedTask(expectedResult, sleepTimeInMilliSeconds));

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
        _MoreFutures.awaitOrThrow(Futures.immediateFailedFuture(new RuntimeException()), MoreFuturesException.class);
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

        MatcherAssert.assertThat(expected, Matchers.is(notNullValue()));
        verify(pending).cancel(true);
    }
}