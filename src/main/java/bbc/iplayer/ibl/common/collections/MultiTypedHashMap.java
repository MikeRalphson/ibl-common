package bbc.iplayer.ibl.common.collections;

import com.google.common.collect.Lists;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@NotThreadSafe
public class MultiTypedHashMap implements MultiTypedMap {

    private Map<Class<?>, Collection<Object>> map;

    public MultiTypedHashMap() {
        this.map = createMap();
    }

    public <T> void putAll(Class<T> token, Collection<T> values) {
        for (T value : values) {
            put(token, value);
        }
    }

    public <T> void put(Class<T> token, T value) {

        checkNotNull(token);
        checkNotNull(value);

        if (map.containsKey(token)) {
            Collection<Object> values = map.get(token);
            values.add(value);

        } else {
            Collection<Object> values = createCollection();
            values.add(value);
            map.put(token, values);
        }
    }

    public <T> Collection<T> get(Class<T> token) {

        checkNotNull(token);

        if (!map.containsKey(token)) {
            return Collections.emptyList();
        }

        return (Collection<T>) map.get(token);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiTypedHashMap that = (MultiTypedHashMap) o;

        if (map != null ? !map.equals(that.map) : that.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return map != null ? map.hashCode() : 0;
    }


    /**
     * Factory method to return an instance of a Collection used to store
     * the Maps values.
     *
     * @return
     */
    protected Collection<Object> createCollection() {
        return Lists.newArrayList();
    }

    /**
     * Factory method to return an instance of a Map implementation.
     *
     * @return
     */
    protected Map<Class<?>, Collection<Object>> createMap() {
        return new HashMap<Class<?>, Collection<Object>>();
    }
}