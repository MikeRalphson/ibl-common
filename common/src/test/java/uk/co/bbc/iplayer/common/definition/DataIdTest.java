package uk.co.bbc.iplayer.common.definition;


import org.junit.Test;

public class DataIdTest {

    @Test(expected = NullPointerException.class)
    public void cannotCreateWithNullID() {
        new DataId(null);
    }
}
