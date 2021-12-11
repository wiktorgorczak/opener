package pl.hackyeah.szczepans.opener.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

public class ValidationApiDto {

    @JsonProperty
    private FileDto signedDocument;

    @JsonProperty
    private FileDto originalDocument;

    @JsonProperty
    private String policy;

    @JsonProperty
    private String tokenExtractionStrategy;

    @JsonProperty
    private String signatureId;

    public ValidationApiDto(File signedFile) {
        this.signedDocument = new FileDto(signedFile);
        this.tokenExtractionStrategy = "NONE";
    }

    public ValidationApiDto(File signedFile, File originalFile) {
        this.signedDocument = new FileDto(signedFile);
        this.originalDocument = new FileDto(originalFile);
        this.tokenExtractionStrategy = "NONE";
    }
}
