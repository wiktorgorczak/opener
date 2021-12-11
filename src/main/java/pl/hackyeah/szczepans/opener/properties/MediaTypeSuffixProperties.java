package pl.hackyeah.szczepans.opener.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hackyeah.szczepans.opener.service.MediaTypeSuffixMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MediaTypeSuffixProperties {

    @Bean
    public MediaTypeSuffixMapper mediaTypeSuffixMapper() throws IOException {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("MediaTypeSuffix.config"))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] splitLine = line.split(":");
                map.put(splitLine[0], splitLine[1]);
            }
        }
        return new MediaTypeSuffixMapper(map);
    }
}
