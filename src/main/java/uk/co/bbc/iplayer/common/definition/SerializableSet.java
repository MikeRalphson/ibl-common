package uk.co.bbc.iplayer.common.definition;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class SerializableSet<K extends Serializable> implements Serializable {

    private final Set<K> set;

    public SerializableSet(Set<K> set) {
        this.set = set;
    }

    public Set<K> getSet() { return set; }

}
