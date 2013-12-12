package uk.co.bbc.iplayer.common.definition;

import org.junit.Test;
import java.io.Serializable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class DataIdTest {

    @Test(expected = NullPointerException.class)
    public void cannotCreateWithNullID() {
        new DataId(null);
    }

    @Test
    public void implementsSerializableForCaching() {
        assertThat(DataId.create("id"), instanceOf(Serializable.class));
    }
}
