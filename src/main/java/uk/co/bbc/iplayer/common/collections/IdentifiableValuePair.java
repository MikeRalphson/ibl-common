package uk.co.bbc.iplayer.common.collections;

import uk.co.bbc.iplayer.common.definition.Identifiable;

/**
 * Simple mechanism for representing a simple relationship between an
 * identifiable (such as a Pid) and any other object.
 *
 * @param <V> the type of Value
 */
public class IdentifiableValuePair<V> {
    private final Identifiable id;
    private final V value;

    /**
     * Allows IdentifiableValuePair to be created using type inference.
     *
     * @param id Identifiable that the value is associated with
     * @param value Value that the id is associated with
     * @return An representation of the relationship between the id and a value
     */
    public static <V> IdentifiableValuePair<V> of(final Identifiable id, final V value) {
        return new IdentifiableValuePair<V>(id, value);
    }

    private IdentifiableValuePair(Identifiable id, V value) {
        this.id = id;
        this.value = value;
    }

    public Identifiable getId() {
        return id;
    }

    public V getValue() {
        return value;
    }
}
