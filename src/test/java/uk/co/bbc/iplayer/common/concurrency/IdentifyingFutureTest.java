package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.*;

import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static uk.co.bbc.iplayer.common.concurrency.Duration.inMilliSeconds;
import static uk.co.bbc.iplayer.common.concurrency.ExecutorServiceUtil.shutdownQuietly;
import static uk.co.bbc.iplayer.common.concurrency.MoreFutures.await;

@RunWith(MockitoJUnitRunner.class)
public class IdentifyingFutureTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ListenableFuture<String> mockedDelegateFuture;

    private ListenableFuture<String> unitWithMockedFuture;
    private ListeningExecutorService executorService;

    @Before
    public void setup() throws ExecutionException, InterruptedException, TimeoutException {

        when(mockedDelegateFuture.get())
                .thenReturn("done");

        when(mockedDelegateFuture.get(anyLong(), any(TimeUnit.class)))
                .thenReturn("done");

        executorService = listeningDecorator(Executors.newFixedThreadPool(2));
        unitWithMockedFuture = new IdentifyingFuture<String>(mockedDelegateFuture, "descriptor", "statsDescriptor");
    }

    @After
    public void tearDown() throws InterruptedException {
        shutdownQuietly(executorService);
    }

    @Test
    public void identifiableTaskOnTimeout() throws MoreFuturesException {

        String descriptor = "task description";
        expectedException.expect(MoreFuturesException.class);
        expectedException.expectMessage("Timed out: " + descriptor);

        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(5000);
                return "complete";
            }
        });

        IdentifyingFuture<String> decorated = new IdentifyingFuture<String>(future, descriptor, "statsDescriptor");
        await(decorated, inMilliSeconds(1000));
    }

    @Test
    public void verifyCancelOnDelegateCalled() {
        unitWithMockedFuture.cancel(true);
        verify(mockedDelegateFuture).cancel(anyBoolean());
    }

    @Test
    public void verfiyGetOnDelegateCalled() throws ExecutionException, InterruptedException {
        unitWithMockedFuture.get();
        verify(mockedDelegateFuture).get();
    }

    @Test
    public void verfiyTimedGetOnDelegateCalled() throws ExecutionException, InterruptedException, TimeoutException {
        unitWithMockedFuture.get(10L, TimeUnit.MINUTES);
        verify(mockedDelegateFuture).get(anyLong(), any(TimeUnit.class));
    }

    @Test
    public void verifyAddListenerOnDelegateCalled() throws ExecutionException, InterruptedException, TimeoutException {
        Runnable runnableTask = mock(RunnableFuture.class);
        unitWithMockedFuture.addListener(runnableTask, executorService);
        verify(mockedDelegateFuture).addListener(any(RunnableFuture.class), any(ExecutorService.class));
    }

    @Test
    public void verifyisDoneOnDelegateCalled() throws ExecutionException, InterruptedException, TimeoutException {
        unitWithMockedFuture.isDone();
        verify(mockedDelegateFuture).isDone();
    }
}
