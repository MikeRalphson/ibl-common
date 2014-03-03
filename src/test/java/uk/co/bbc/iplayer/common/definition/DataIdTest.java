package uk.co.bbc.iplayer.common.definition;

import com.google.common.collect.Maps;
import org.junit.Test;
import java.io.Serializable;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    public void verifyAssignfromDataId() {

        String KEY = "pid1";

        final Map<Identifiable, Integer> pidMap = Maps.newConcurrentMap();

        // create keys of all known subtypes using the same string KEY (what equals is based on)
        pidMap.put(new DataId(KEY), 1);
        pidMap.put(new VPid(KEY), 1);
        pidMap.put(new Pid(KEY), 1);
        pidMap.put(new MID(KEY), 1);

        assertThat(pidMap.size(), is(1));
        assertThat(pidMap.containsKey(new DataId(KEY)), is(true));
        assertThat(pidMap.containsKey(new VPid(KEY)), is(true));
        assertThat(pidMap.containsKey(new Pid(KEY)), is(true));
        assertThat(pidMap.containsKey(new MID(KEY)), is(true));
    }
}