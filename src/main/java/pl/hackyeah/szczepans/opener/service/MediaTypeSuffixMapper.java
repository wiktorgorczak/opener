package pl.hackyeah.szczepans.opener.service;

import java.util.Map;

public class MediaTypeSuffixMapper {

    private final Map<String, String> map;

    public MediaTypeSuffixMapper(Map<String, String> map) {
        this.map = map;
    }

    public String getSuffixByMediaType(String mediaType) {
        return map.get(mediaType);
    }
}
