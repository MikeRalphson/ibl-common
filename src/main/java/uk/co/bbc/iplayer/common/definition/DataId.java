package uk.co.bbc.iplayer.common.definition;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    public boolean equals(final Object o) {
        return org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static Identifiable create(String id) {
        return new DataId(id);
    }

    @Override
    public String toString() {
        return "DataId{" +
                "id='" + id + '\'' +
                '}';
    }
}
