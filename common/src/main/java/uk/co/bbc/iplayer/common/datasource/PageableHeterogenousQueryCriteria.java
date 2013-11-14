package uk.co.bbc.iplayer.common.datasource;

public class PageableHeterogenousQueryCriteria extends HeterogenousQueryCriteria implements Pageable {

    private final int page;

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    private final int pageSize;

    public PageableHeterogenousQueryCriteria(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
