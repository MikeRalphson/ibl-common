package bbc.iplayer.ibl.common.marshalling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SampleRootElementJson {


    @XmlElement(name = "sampleelements")
    private List<SampleElement> sampleElements;

    public List<SampleElement> getSampleElements() {
        return sampleElements;
    }
}

