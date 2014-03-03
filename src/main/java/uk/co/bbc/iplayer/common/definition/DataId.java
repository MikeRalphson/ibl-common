package uk.co.bbc.iplayer.common.definition;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) {
            return false;
        }

        DataId that = (DataId) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .build();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).build();
    }

    public static Identifiable create(String id) {
        return new DataId(id);
    }
}
