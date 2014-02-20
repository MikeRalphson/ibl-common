package uk.co.bbc.iplayer.common.definition;

public class SortDefinition {

    private String sortBy;
    private SortDirection sortDirection = SortDirection.ASCENDING;

    public static enum SortDirection {
        ASCENDING,
        DESCENDING;

        public String getValue() {
            return this.name().toLowerCase();
        }
    }

    public SortDefinition(String sortBy, SortDirection sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public static SortDefinition create(String sortBy, SortDirection sortDirection) {
        return new SortDefinition(sortBy, sortDirection);
    }
}


