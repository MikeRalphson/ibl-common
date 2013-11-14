package bbc.iplayer.ibl.common.datasource;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class HeterogenousQueryCriteria {

    private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

    public <T> void put(Class<T> type, T instance) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(instance);

        map.put(type, instance);
    }

    public <T> Optional<T> get(Class<T> type) {
        return Optional.fromNullable(type.cast(map.get(type)));
    }
}
