package pl.hackyeah.szczepans.opener.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hackyeah.szczepans.opener.service.MediaTypeProgramMapper;
import pl.hackyeah.szczepans.opener.service.MediaTypeSuffixMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MediaTypeProperties {

    @Bean
    public MediaTypeSuffixMapper mediaTypeSuffixMapper() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("MediaTypeSuffix.config");
        Map<String, String> map = loadFromFileToMap(is);
        return new MediaTypeSuffixMapper(map);
    }

    @Bean
    public MediaTypeProgramMapper mediaTypeProgramMapper() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("MediaTypeProgram.config");
        Map<String, String> map = loadFromFileToMap(is);
        return new MediaTypeProgramMapper(map);
    }

    private Map<String, String> loadFromFileToMap(InputStream is) throws IOException {
        Map<String, String> map = new HashMap<>();
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(streamReader)) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] splitLine = line.split(":");
                map.put(splitLine[0], splitLine[1]);
            }
        }
        return map;
    }
}
