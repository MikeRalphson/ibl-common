package uk.co.bbc.iplayer.common.collections;

import org.junit.Test;
import uk.co.bbc.iplayer.common.definition.Identifiable;
import uk.co.bbc.iplayer.common.definition.Pid;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IdentifiableValuePairTest {

    private static final Pid PID = new Pid("b12345678");
    private static final Object VALUE = new Object();

    @Test
    public void testOf() throws Exception {
        final IdentifiableValuePair<Object> pair = IdentifiableValuePair.of(PID, VALUE);
        assertThat(pair.getId(), is((Identifiable) PID));
        assertThat(pair.getValue(), is(VALUE));
    }
}
