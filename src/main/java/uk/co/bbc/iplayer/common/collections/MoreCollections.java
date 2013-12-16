package uk.co.bbc.iplayer.common.collections;

public class MoreCollections {

    private MoreCollections() {
        throw new AssertionError();
    }

    public static MultiTypedMap newMultiTypedMap() {
        return new MultiTypedHashMap();
    }

    public static MultiTypedMap synchronizedMultiTypedMap() {
        // place holder
        throw new UnsupportedOperationException();
    }
}
