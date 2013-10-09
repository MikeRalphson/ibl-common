package bbc.iplayer.common.marshalling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SampleElement {

    @XmlElement(name = "ref")
    private String ref;

    public String getRef() {
        return ref;
    }
}
