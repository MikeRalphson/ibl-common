package bbc.iplayer.ibl.common.collections;

import com.google.common.collect.Lists;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MultiTypeContainerTest {

    private static final Integer DEFAULT_INT = new Integer(1);
    private static final String DEFAULT_STRING = "test";

    @Test
    public void anObjectCanBePutAndRetrieved() {

        MultiTypedMap map = populateMapWith(Integer.class, DEFAULT_INT, 1);

        Collection<Integer> integers = map.get(Integer.class);
        assertThat(integers.size(), CoreMatchers.is(1));

        final Integer first = Lists.newArrayList(integers).get(0);
        assertThat(first, is(equalTo(DEFAULT_INT)));
    }

    @Test
    public void emptyListOfValuesIfNoMatchingToken() {
        MultiTypedMap map = new MultiTypedHashMap();
        assertThat(map.get(String.class).size(), is(0));
    }

    @Test
    public void populatedMapFromACollection() {
        Collection values = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        MultiTypedMap map = MoreCollections.newMultiTypedMap();
        map.putAll(Integer.class, values);

        assertThat(map.get(Integer.class).size(), CoreMatchers.is(values.size()));
    }

    @Test
    public void sizeZeroWhenNotPopulated() {
        MultiTypedMap map = new MultiTypedHashMap();
        assertThat(map.size(), is(0));
    }

    @Test
    public void containsMultipleTypes() {
        MultiTypedMap map = new MultiTypedHashMap();
        map.put(Integer.class, DEFAULT_INT);
        map.put(String.class, DEFAULT_STRING);

        assertThat(map.size(), CoreMatchers.is(2));
    }

    @Test
    public void multipleValuesOfTheSameType() {
        int n = 50;
        MultiTypedMap map = populateMapWith(Integer.class, DEFAULT_INT, n);
        // Only one key
        assertThat(map.size(), CoreMatchers.is(1));
        // but has n values
        assertThat(map.get(Integer.class).size(), CoreMatchers.is(n));
    }

    @Test
    public void clearMapAfterBeingPopulated() {
        int n = 10;
        MultiTypedMap map = populateMapWith(Integer.class, DEFAULT_INT, n);
        assertThat(map.isEmpty(), CoreMatchers.is(false));

        map.clear();
        assertThat(map.isEmpty(), CoreMatchers.is(true));
    }

    @Test
    public void isEmtyTrueWhenNotPopulated() {
        MultiTypedMap map = new MultiTypedHashMap();
        assertThat(map.isEmpty(), is(true));
    }

    @Test
    public void multiMapsAreNotTheSameHaveDifferntHashcode() {
        MultiTypedMap map1 = populateMapWith(Integer.class, DEFAULT_INT, 10);
        MultiTypedMap map2 = populateMapWith(Integer.class, DEFAULT_INT, 11);

        assertThat(map1, not(CoreMatchers.is(map2)));
    }


    @Test
    public void multiMapsThatAreTheSameHaveTheSameHashcode() {
        MultiTypedMap map1 = populateMapWith(Integer.class, DEFAULT_INT, 10);
        MultiTypedMap map2 = populateMapWith(Integer.class, DEFAULT_INT, 10);

        assertThat(map1, CoreMatchers.is(map2));
    }

    private <T> MultiTypedMap populateMapWith(Class<T> type, T value, int times) {

        MultiTypedMap map = new MultiTypedHashMap();

        for (int i = 0; i < times; i++) {
            map.put(type, value);
        }

        return map;
    }

}
