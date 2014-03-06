package uk.co.bbc.iplayer.common.definition;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.builder.HashCodeBuilder;
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

        if (o == null || !Identifiable.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        Identifiable that = (Identifiable) o;

        return new EqualsBuilder()
                .append(id, that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37).append(id).hashCode();
    }

    public static Identifiable create(String id) {
        return new DataId(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
