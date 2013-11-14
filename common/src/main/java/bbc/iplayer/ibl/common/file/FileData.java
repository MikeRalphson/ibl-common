package bbc.iplayer.ibl.common.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

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
}
