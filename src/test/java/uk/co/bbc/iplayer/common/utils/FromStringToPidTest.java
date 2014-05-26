package uk.co.bbc.iplayer.common.utils;

import com.google.common.collect.Lists;
import org.junit.Test;
import uk.co.bbc.iplayer.common.definition.Pid;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FromStringToPidTest {

    @Test
    public void createPidFromString() {
        String id = "b0000011";
        Pid pid = new FromStringToPid().apply(id);
        assertThat(pid.getId(), is(id));
    }

    @Test
    public void convertListOfStrings() {

        String id1 = "b0000011";
        String id2 = "b0000012";
        String id3 = "b0000013";

        List<String> ids = Lists.newArrayList(id1, id2, id3);

        List<Pid> pids = FromStringToPid.fromList(ids);
        assertThat(pids.get(0).getId(), is(id1));
        assertThat(pids.get(1).getId(), is(id2));
        assertThat(pids.get(2).getId(), is(id3));
    }
}
