package uk.co.bbc.iplayer.common.definition;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SerializableListTest {

    @Test
    public void getListReturnsUnderlyingList() {
        List<String> list = Arrays.asList("foo", "bar");
        SerializableList<String> serializableList = new SerializableList<String>(list);
        assertEquals(list, serializableList.getList());
    }

}