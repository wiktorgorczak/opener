package pl.hackyeah.szczepans.opener.service;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class FileTypeDetector {

	private static final Logger logger = LoggerFactory.getLogger(FileTypeDetector.class);
	private static final Pattern pattern = Pattern.compile("\\.[^\\.]+$"); 
	
	@Value("#{'${suffixes}'.split(',')}") 
	private List<String> suffixes;
	
    private final Tika tika;
    private final MediaTypeSuffixMapper mapper;

    public FileTypeDetector(MediaTypeSuffixMapper mapper) {
        this.tika = new Tika();
        this.mapper = mapper;
    }

    public Triple<String, String, String> checkFileType(File file) throws IOException {
    	String absoultePath = file.getAbsolutePath();    	    
    	String newPath = "";
    	String expectedSuffix = "";
    	String realSuffix = null;
    	boolean toDelete = false;    	
    	for(String suffix : suffixes) {
    		if(absoultePath.endsWith(suffix)) {
    			newPath = pattern.matcher(absoultePath).replaceAll("");
        		Files.copy(Path.of(absoultePath), Path.of(newPath), StandardCopyOption.REPLACE_EXISTING);
        		toDelete = true;
        		expectedSuffix = suffix;
        		break;
    		}
    	}    	
    	if(newPath.equals("")) {
    		newPath = absoultePath;
    	}    	
    	File copied = new File(newPath);    	   	    	
        Optional<String> fileType = Optional.empty();
        try {
            fileType = Optional.of(tika.detect(copied));
            if(toDelete) {
            	Files.delete(Path.of(newPath));
            }
            if(fileType.isPresent()) {
            	realSuffix = mapper.getSuffixByMediaType(fileType.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Triple<String, String, String> result = Triple.of(fileType.orElse("unknown"), expectedSuffix, realSuffix);
        return result;
    }
}
