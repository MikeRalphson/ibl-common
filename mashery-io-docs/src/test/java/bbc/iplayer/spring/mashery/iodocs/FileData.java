package bbc.iplayer.spring.mashery.iodocs;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class FileData {
    public static File getFileFromClassPath(String path) {
        try {
            return new ClassPathResource(path).getFile();
        } catch (IOException ex) {
            throw new RuntimeException("Failed getting " + path);
        }
    }
}
