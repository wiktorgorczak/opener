package pl.hackyeah.szczepans.opener.service;

import java.util.Map;

public class MediaTypeProgramMapper {

    private final Map<String, String> map;

    public MediaTypeProgramMapper(Map<String, String> map) {
        this.map = map;
    }

    public String getProgramByMediaType(String mediaType) {
        return map.get(mediaType);
    }
}
