package uk.co.bbc.iplayer.common.collections;

import com.google.common.collect.Lists;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class ResultsList<T extends Serializable> implements Serializable {

    private static int DEFAULT_PAGE = 1;

    private final List<T> list;
    private final Long total;
    private final Integer page;
    private final Integer pageSize;

    /**
     * A ResultsList is composed of a regular list and pagination information such as
     * the total number, page and page size where the elements within the ResultsList
     * should be treated as a single page.
     *
     * @param list Items that form the current page
     * @param total Total number of items available
     * @param page Which page number this is
     * @param pageSize How many items per page
     */
    public ResultsList(List<T> list, Long total, Integer page, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * Allows you to create a list without specifying the type.
     *
     * @param list Items that form the current page
     * @param total Total number of items available
     * @param page Which page number this is
     * @param pageSize How many items per page
     * @param <T> Type of item
     * @return ResultsList containing
     */
    public static <T extends Serializable> ResultsList<T> create(List<T> list, Long total, Integer page, Integer pageSize) {
        return new ResultsList<T>(list, total, page, pageSize);
    }

    public ResultsList(List<T> list) {
        this.list = list;
        this.total = Long.valueOf(list.size());
        this.page = DEFAULT_PAGE;
        this.pageSize = list.size();
    }

    public static <T extends Serializable> ResultsList<T> create(List<T> list) {
        return new ResultsList<T>(list);
    }

    public ResultsList(T... items) {
        this.list = Lists.newArrayList(items);
        this.total = Long.valueOf(items.length);
        this.page = DEFAULT_PAGE;
        this.pageSize = items.length;
    }

    public static <T extends Serializable> ResultsList<T> create(T... items) {
        return new ResultsList<T>(items);
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
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
