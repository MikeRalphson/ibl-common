package bbc.iplayer.ibl.common.file;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFromResource {
    public static Properties getProperties(Resource resource) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}