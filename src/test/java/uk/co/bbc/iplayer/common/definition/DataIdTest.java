package uk.co.bbc.iplayer.common.definition;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.StopWatch;
import java.io.Serializable;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DataIdTest {

    @Test(expected = NullPointerException.class)
    public void cannotCreateWithNullID() {
        new DataId(null);
    }

    @Test
    public void implementsSerializableForCaching() {
        assertThat(DataId.create("id"), instanceOf(Serializable.class));
    }

    @Test
    public void verifyIdComparision() {
        DataId id1 = new DataId("1");
        DataId duplicateOfId1 = new DataId("1");
        DataId id2 = new DataId("2");

        assertThat(id1, is(duplicateOfId1));
        assertThat(id1, not(is(id2)));
    }

    @Test
    public void timeEquals() {

        DataId target = new DataId("1");
        List<DataId> dataIds = Lists.newArrayList();

        for (int i = 0; i < 1000; i++) {
            dataIds.add(new DataId(Integer.toString(i)));
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (DataId dataId : dataIds) {
           dataId.equals(target);
        }
        stopWatch.stop();

        System.out.println("TIME: " + stopWatch.getTotalTimeMillis());

    }

    @Test
    public void timeToString() {

        List<Pid> dataIds = Lists.newArrayList();

        for (int i = 0; i < 10000; i++) {
            dataIds.add(new Pid("b03wvbtz"));
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (Pid dataId : dataIds) {
            dataId.toString();
        }
        stopWatch.stop();

        System.out.println("toString: " + stopWatch.getTotalTimeMillis());

    }
}