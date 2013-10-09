package bbc.iplayer.common.marshalling;

import bbc.iplayer.ibl.common.schema.adapters.IBLDateTimeAdapter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.URI;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nitro", propOrder = {
        "version"
})
@XmlRootElement(name = "ibl")
public class TestIBL {

    @XmlAttribute(required = true)
    private String version = "0.1";
    @XmlAttribute(required = true)
    private URI schema;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(IBLDateTimeAdapter.class)
    private DateTime timestamp;

    public TestIBL() {
        timestamp = new DateTime(DateTimeZone.UTC);
    }


    public URI getSchema() {
        return schema;
    }

    public void setSchema(URI schema) {
        this.schema = schema;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}