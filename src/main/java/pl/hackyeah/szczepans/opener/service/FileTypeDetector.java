package pl.hackyeah.szczepans.opener.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class FileTypeDetector {

    private final Tika tika;

    public FileTypeDetector() {
        this.tika = new Tika();
    }

    public Optional<String> checkFileType(File file) {
        Optional<String> fileType = Optional.empty();
        try {
            fileType = Optional.of(tika.detect(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileType;
    }
}
