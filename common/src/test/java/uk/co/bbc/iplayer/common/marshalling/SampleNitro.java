package uk.co.bbc.iplayer.common.marshalling;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nitro", propOrder = {
        "feedContent"
})
@XmlRootElement(name = "nitro")
public class SampleNitro {


    @XmlElements({
            @XmlElement(name = "results", type = FeedResultsXml.class)
    })
    //feedContent
    protected Object feedContent;


    public Object getFeedContent() {
        return feedContent;
    }

    public void setFeedContent(Object value) {
        this.feedContent = value;
    }


}
