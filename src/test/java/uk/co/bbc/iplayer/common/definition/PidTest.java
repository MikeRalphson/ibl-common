package uk.co.bbc.iplayer.common.definition;

import com.google.common.collect.Lists;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PidTest {

    private static void valid(String pid) {
        assertThat(pid, is(new Pid(pid).getId()));
    }

    @Test
    public void pidIsAlphanumber() throws Exception {
        valid("b0144p0z");
    }

    @Test
    public void pidCanBe8CharsOrMore() {
        valid("b1234567");
    }

    @Test
    public void pidCanBeMoreThan8Chars() {
        valid("123456789bcdfghjklmnpqrstvwxyz123456789bcdfghjklmnpqrstvwxyz");
    }

    @Test
    public void pidCanBeJustLetters() {
        valid("bnmcbnmc");
    }
    @Test
    public void pidCanBeJustNumbers() {
        valid("12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void pidCantBe7OrLessChars() {
        new Pid("1234567");
    }

    @Test(expected = IllegalArgumentException.class)
    public void pidCantHaveVowels() {
        new Pid("1234567a");
    }

    @Test
    public void testFromPids() throws Exception {
        List<String> strings = Lists.newArrayList("b0000001", "d0000001");
        List<Pid> pids = Lists.newArrayList();
        for (String string : strings) {
            pids.add(new Pid(string));
        }

        assertThat(strings, is(Pid.fromPids(pids)));
    }

    @Test
    public void implementsSerializableForCaching() {
        MatcherAssert.assertThat(DataId.create("id"), instanceOf(Serializable.class));
    }
}
