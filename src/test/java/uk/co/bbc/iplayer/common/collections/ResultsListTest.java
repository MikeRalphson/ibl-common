package uk.co.bbc.iplayer.common.collections;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class ResultsListTest {
    private static List<String> ITEMS = Lists.newArrayList("alpha", "bravo", "charlie");
    private static long TOTAL = 15;
    private static int PAGE = 2;
    private static int PAGE_SIZE = 5;

    private static String TEST_STRING_1 = "test1";
    private static String TEST_STRING_2 = "test2";

    @Test
    public void testCreateAllDetails() throws Exception {
        ResultsList<String> resultsList = new ResultsList<String>(ITEMS, TOTAL, PAGE, PAGE_SIZE);
        assertThat(resultsList.elements(), is(ITEMS));
    }

    @Test
    public void testCreateItems() throws Exception {
        ResultsList<String> resultsList = new ResultsList<String>(ITEMS);

        assertThat(resultsList.elements(), is(ITEMS));
        assertThat(resultsList.total(), is((long) ITEMS.size()));
        assertThat(resultsList.page(), is(1));
        assertThat(resultsList.pageSize(), is(ITEMS.size()));
    }

    @Test
    public void testCreate() throws Exception {
        ResultsList<String> resultsList = new ResultsList<String>(TEST_STRING_1, TEST_STRING_2);

        assertThat(resultsList.elements().get(0), is(TEST_STRING_1));
        assertThat(resultsList.elements().get(1), is(TEST_STRING_2));

        int testStringCount = 2;
        assertThat(resultsList.total(), is((long) testStringCount));
        assertThat(resultsList.page(), is(1));
        assertThat(resultsList.pageSize(), is(testStringCount));
    }

    @Test
    public void testStaticCreateMethodWithList() throws Exception {
        ResultsList<String> list = ResultsList.create(ITEMS);
        assertThat(list, is(new ResultsList<String>(ITEMS)));
    }

    @Test
    public void testStaticCreateMethodWithVarArgs() throws Exception {
        ResultsList<String> list = ResultsList.create(TEST_STRING_1, TEST_STRING_2);
        assertThat(list, is(new ResultsList<String>(TEST_STRING_1, TEST_STRING_2)));
    }

    @Test
    public void testToString() throws Exception {
        ResultsList<String> resultsList = ResultsList.create(ITEMS, TOTAL, PAGE, PAGE_SIZE);

        assertThat(resultsList.toString(), containsString("total=" + TOTAL));
        assertThat(resultsList.toString(), containsString("page=" + PAGE));
        assertThat(resultsList.toString(), containsString("pageSize=" + PAGE_SIZE));
        for (String item : ITEMS) {
            assertThat(resultsList.toString(), containsString(item));
        }
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(
                ResultsList.create(ITEMS, TOTAL, PAGE, PAGE_SIZE)
                        .equals(ResultsList.create(ITEMS, TOTAL, PAGE, PAGE_SIZE))
        );
    }
}
