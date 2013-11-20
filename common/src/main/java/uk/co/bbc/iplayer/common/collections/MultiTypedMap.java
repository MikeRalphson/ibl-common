package uk.co.bbc.iplayer.common.collections;

import java.util.Collection;

// Can't use Map interface as values() would return an untype safe list
public interface MultiTypedMap {

    boolean containsKey(Class<?> key);

    <T> void put(Class<T> type, T object);

    <T> void putAll(Class<T> type, Collection<T> objects);

    <T> Collection<T> get(Class<T> type);

    int size();

    boolean isEmpty();

    void clear();
}
