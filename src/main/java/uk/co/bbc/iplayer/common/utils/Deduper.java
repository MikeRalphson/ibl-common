package uk.co.bbc.iplayer.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public final class Deduper {

    private Deduper() {
    }

    public static <T> List<T> deDupe(List<T> items) {
        return new ArrayList<T>(new LinkedHashSet<T>(items));
    }

    public static <T> List<T> deDupe(T[] items) {
        return deDupe(Arrays.asList(items));
    }
}