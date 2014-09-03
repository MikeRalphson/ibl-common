package uk.co.bbc.iplayer.common.definition;

import java.io.Serializable;
import java.util.List;

public class SerializableList<K extends Serializable> implements Serializable {

    private final List<K> list;

    public SerializableList(List<K> list) {
        this.list = list;
    }

    public List<K> getList() {
        return list;
    }

}
