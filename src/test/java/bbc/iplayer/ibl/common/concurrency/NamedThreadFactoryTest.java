package bbc.iplayer.ibl.common.concurrency;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NamedThreadFactoryTest {

    @Test
    public void createNamedThread() {

        String poolName = "IBL-THREAD-POOL";

        NamedThreadFactory factory = new NamedThreadFactory(poolName);
        Thread thread = factory.newThread(new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        });

        assertThat(thread.getName(), is(poolName + ":" + 1));
        assertThat(thread, instanceOf(NamedThread.class));
    }
}
