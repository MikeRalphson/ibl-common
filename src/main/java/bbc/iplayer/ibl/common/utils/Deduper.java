package bbc.iplayer.ibl.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Deduper {
    private Deduper() {

    }

    public static <T> List<T> deDupe(List<T> items) {

        Set<T> tags = new HashSet<T>();
        List<T> result = new ArrayList<T>();

        for (T item : items) {
            if (!tags.contains(item)) {
                result.add(item);
                tags.add(item);
            }
        }

        return result;
    }

    public static <T> List<T> deDupe(T[] items) {
        Set<T> tags = new HashSet<T>();
        List<T> result = new ArrayList<T>();

        for (T item : items) {
            if (!tags.contains(item)) {
                result.add(item);
                tags.add(item);
            }
        }
        return result;
    }
}