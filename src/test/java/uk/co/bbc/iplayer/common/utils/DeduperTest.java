package uk.co.bbc.iplayer.common.utils;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.co.bbc.iplayer.common.definition.Pid;

import static org.junit.Assert.assertThat;

public class DeduperTest {

    @Test
    public void dedupes() throws Exception {

        Pid pid1 = new Pid("p1111111");
        Pid pid2 = new Pid("p1111112");
        assertThat(Deduper.deDupe(Lists.newArrayList(pid1, pid1, pid2)), Matchers.hasSize(2));

    }

    @Test
    public void dedupesArray() throws Exception {

        Pid pid1 = new Pid("p1111111");
        Pid pid2 = new Pid("p1111112");
        assertThat(Deduper.deDupe(new Pid[]{pid1, pid1, pid2}), Matchers.hasSize(2));

    }


}
