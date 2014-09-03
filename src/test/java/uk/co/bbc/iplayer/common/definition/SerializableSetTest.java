package uk.co.bbc.iplayer.common.definition;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SerializableSetTest {

    @Test
    public void getSetReturnsUnderlyingSet() {
        Set<String> set = new HashSet<String>();
        set.add("foo");
        set.add("bar");
        SerializableSet<String> serializableSet = new SerializableSet<String>(set);
        assertEquals(set, serializableSet.getSet());
    }

}