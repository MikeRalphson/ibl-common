package bbc.iplayer.ibl.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Deduper {
    private Deduper() {

    }

    public static <T> List<T> deDupe(List<T> pids) {

        Set<T> tags = new HashSet<T>();
        List<T> result = new ArrayList<T>();

        for (T pid : pids) {
            if (!tags.contains(pid)) {
                result.add(pid);
                tags.add(pid);
            }
        }

        return result;
    }
}