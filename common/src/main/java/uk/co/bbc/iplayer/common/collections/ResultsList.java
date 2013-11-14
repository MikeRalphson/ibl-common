package uk.co.bbc.iplayer.common.collections;

import com.google.common.collect.Lists;

import java.util.List;

public class ResultsList<T> {

    private final List<T> list;
    private final Long total;
    private final Integer page;
    private final Integer pageSize;

    public ResultsList(List<T> list, Long total, Integer page, Integer pageSize) {
        this.list = list;

        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }

    public ResultsList(List<T> list) {
        this.list = list;
        this.total = Long.valueOf(list.size());
        this.page = 1;
        this.pageSize = list.size();
    }

    public ResultsList(T... items) {
        this.list = Lists.newArrayList(items);
        this.total = Long.valueOf(items.length);
        this.page = 1;
        this.pageSize = items.length;
    }

    public Long total() {
        return total;
    }

    public List<T> elements() {
        return list;
    }

    public Integer page() {
        return page;
    }

    public Integer pageSize() {
        return pageSize;
    }

    @Override
    public String toString() {
        return "FeedResultsList{" +
                "total=" + total +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
