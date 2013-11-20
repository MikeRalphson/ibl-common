package bbc.iplayer.spring.mashery.iodocs.model;

public class ResourceName {
    public String getName() {
        return name;
    }

    private final String name;

    public ResourceName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceName that = (ResourceName) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
