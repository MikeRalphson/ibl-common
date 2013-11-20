package uk.co.bbc.iplayer.common.marshalling;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "feedResultsXml", propOrder = {
})
public class FeedResultsXml {

    @XmlAttribute
    protected Integer page;
    @XmlAttribute
    protected Long total;
    @XmlAttribute(name = "page_size")
    protected Integer pageSize;

    @XmlAttribute(name = "more_than")
    protected Long moreThan;


    public Integer getPage() {
        return page;
    }


    public void setPage(Integer value) {
        this.page = value;
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long value) {
        this.total = value;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

    public Long getMoreThan() {
        return moreThan;
    }

    public void setMoreThan(Long moreThan) {
        this.moreThan = moreThan;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
