package uk.co.bbc.iplayer.common.definition;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortDefinitionTest {

    private static final String MY_FIELD = "myField";
    private static final SortDefinition.SortDirection DIRECTION = SortDefinition.SortDirection.ASCENDING;
    private SortDefinition sortDefinition;

    @Before
    public void setup() {
        sortDefinition = SortDefinition.create(MY_FIELD, DIRECTION);
    }

    @Test
    public void sortByField() {
        assertThat(sortDefinition.getSortBy(), is(MY_FIELD));
    }

    @Test
    public void sortDirection() {
        assertThat(sortDefinition.getSortDirection(), is(DIRECTION));
    }

    @Test
    public void sortDirectionValueInLowerCase() {
        assertThat(sortDefinition.getSortDirection().getValue(), is("ascending"));
    }
}
