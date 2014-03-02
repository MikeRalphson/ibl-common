package uk.co.bbc.iplayer.common.definition;

import com.google.common.base.Preconditions;

public class DataId implements Identifiable {

    private final String id;

    public DataId(String id) {
        Preconditions.checkNotNull(id);
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataId dataId = (DataId) o;

        if (!id.equals(dataId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public String toString() {
        return "DataId{" +
                "id='" + id + '\'' +
                '}';
    }

    public static Identifiable create(String id) {
        return new DataId(id);
    }
}
