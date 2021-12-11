package pl.hackyeah.szczepans.opener.controller.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileDto {

    @JsonProperty
    private byte[] bytes;

    @JsonProperty
    private String digestAlgorithm;

    @JsonProperty
    private String name;

    public FileDto() {
    }

    public FileDto(File file) {
        try {
            this.bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            this.bytes = null;
        }
        this.name = file.getName();
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
