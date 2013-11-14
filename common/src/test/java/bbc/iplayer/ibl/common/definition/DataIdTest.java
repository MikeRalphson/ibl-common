package bbc.iplayer.ibl.common.definition;


import org.junit.Test;

public class DataIdTest {

    @Test(expected = NullPointerException.class)
    public void cannotCreateWithNullID() {
        new DataId(null);
    }
}
