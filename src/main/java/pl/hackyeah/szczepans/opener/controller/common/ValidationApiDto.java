package pl.hackyeah.szczepans.opener.controller.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ValidationApiDto {

    @JsonProperty
    private FileDto signedDocument;

    @JsonProperty
    private List<FileDto> originalDocuments;

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
        this.originalDocuments = Collections.singletonList(new FileDto(originalFile));
        this.tokenExtractionStrategy = "NONE";
    }
}
