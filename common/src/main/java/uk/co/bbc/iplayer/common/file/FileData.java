package uk.co.bbc.iplayer.common.file;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class FileData {

    private static final Logger LOG = LoggerFactory.getLogger(FileData.class);

    public static File getFileFromClassPath(String path) {
        try {
            return new ClassPathResource(path).getFile();
        } catch (IOException ex) {
            LoggerFactory.getLogger(FileData.class).error("unable to load " + path, ex);
            throw new FileLoadFailureException();
        }
    }

    public static <T> T getDataFromFileClassPath(Class<T> clazz, String path) throws UnmarshallerCreationException {
        return getDataFromFile(clazz, getFileFromClassPath(path));
    }

    public static <T> T getDataFromFile(Class<T> clazz, File cpr) throws UnmarshallerCreationException {
        Unmarshaller unmarshaller = UnmarshallerFactory.getUnmarshaller(clazz);
        try {
            return (T) unmarshaller.unmarshal(cpr);
        } catch (JAXBException ex) {
            LOG.error("File: " + cpr, ex);
            throw new FileLoadFailureException();
        }
    }

    public static String getFileContentsAsString(final String filename) throws IOException {
        return Resources.toString(getResource(filename), Charset.forName("UTF-8"));
    }

    public static StreamSource createStreamSource(final String file) throws IOException {

        ClassPathResource resource = new ClassPathResource(file);
        if (!resource.exists()) {
            throw new IOException("Can't find file " + file);
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
