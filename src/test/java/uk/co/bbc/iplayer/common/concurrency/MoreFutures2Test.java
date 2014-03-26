package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
    public void createFutureChainUsingDefaultExecutorService() throws Exception {

        List<String> aggregation = MoreFutures2
                .chain(String.class)
                .add(createTask("1"))
                .add(createTask("2"))
                .add(createTask("3"))
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
    public void customAggregation() throws Exception {
        Integer length = MoreFutures2
                .chain(String.class)
                .addAll(newArrayList(createTask("I"), createTask("II"), createTask("III")))
                .usingExecutorService(executorService)
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
    public void verifyExceptionConvertionWhenAnExceptionIsThrown() throws Exception {

        expectedException.expect(TestFutureException.class);
        expectedException.expectMessage("raised from task");

        MoreFutures2
                .chain(String.class)
                .add(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        throw new Exception("raised from task");
                    }
                })
                .onExceptionThrow(TestFutureException.class)
                .usingExecutorService(executorService)
                .aggregate();
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

    @Test
    public void successfulGetFromPipeableFuture() throws ExecutionException, InterruptedException {

        String sourceInput = "original";

        ListenableFuture<String> sourceFuture = Futures.immediateFuture(sourceInput);

        // disallow direct construction - only through moreFutures
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

        // PipeableFutureTask to allow futures to be chain (using transform)
        ListenableFuture<Boolean> future = Futures.immediateFuture(true);

        PipeableFuture<String> pipeableFuture = MoreFutures2
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

        ListenableFuture<String> future = Futures.immediateFuture(input);

        PipeableFuture<Map<String, Integer>> pipeableFuture = MoreFutures2
                .pipe(future)
                    .to(new StringToASCII())
                    .to(new ASCIIToString())
                    .to(new CreateStringLengthMap());

        Map<String, Integer> stringIntegerMap = pipeableFuture.get();

        assertThat(stringIntegerMap.size(), is(1));
        assertThat(stringIntegerMap.get(input), is(input.length()));
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