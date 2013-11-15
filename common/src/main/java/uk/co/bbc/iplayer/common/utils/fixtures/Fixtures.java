package uk.co.bbc.iplayer.common.utils.fixtures;

import com.google.common.io.Resources;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

public class Fixtures {
    public static String getFileContentsAsString(final String filename) throws IOException {
        return Resources.toString(getResource("fixtures/" + filename), Charset.forName("UTF-8"));
    }

    public static StreamSource createStreamSource(final String file) throws IOException {
        final String filePath = "fixtures/" + file;
        ClassPathResource resource = new ClassPathResource(filePath);
        if (!resource.exists()) {
            throw new IOException("Can't find fixture " + filePath);
        }

        StreamSource source = new StreamSource();
        source.setInputStream(resource.getInputStream());

        return source;
    }

    public static <T> T unmarshal(Class<T> type, String file) throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller(type);
        File xml = new File(file);
        return (T) unmarshaller.unmarshal(xml);
    }

    public static <T> T unmarshalContent(Class<T> type, String content) throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller(type);
        return (T) unmarshaller.unmarshal(new ByteArrayInputStream(content.getBytes()));
    }

    private static <T> Unmarshaller createUnmarshaller(Class<T> root) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(root);
        return jc.createUnmarshaller();
    }
}

