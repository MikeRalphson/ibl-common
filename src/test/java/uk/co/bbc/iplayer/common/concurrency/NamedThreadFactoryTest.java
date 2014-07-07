package uk.co.bbc.iplayer.common.concurrency;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class NamedThreadFactoryTest {

    private static String POOL_NAME = "IBL-THREAD-POOL";
    private NamedThreadFactory factory;

    @Before
    public void setup() {
        factory = new NamedThreadFactory(POOL_NAME);
    }

    @Test
    public void createNamedThread() {

        Thread thread = factory.newThread(TaskFactory.createNoOpRunnable());

        assertThat(thread.getName(), startsWith(POOL_NAME));
        assertThat(thread, instanceOf(NamedThread.class));
    }
}
