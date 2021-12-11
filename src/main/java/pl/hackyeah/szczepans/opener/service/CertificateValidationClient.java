package pl.hackyeah.szczepans.opener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.hackyeah.szczepans.opener.controller.common.FileDto;
import pl.hackyeah.szczepans.opener.controller.common.ValidationApiDto;
import pl.hackyeah.szczepans.opener.properties.ValidationApiProperties;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Component
public class CertificateValidationClient {

    private final String validationApiUrl;
    private final RestTemplate restTemplate;

    public CertificateValidationClient(ValidationApiProperties validationApiProperties) {
        this.validationApiUrl = validationApiProperties.getHost();
        this.restTemplate = new RestTemplate();
    }

    public Optional<FileDto[]> getOriginalDocument(File signedFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile);
        Optional<FileDto[]> response = Optional.empty();
        try {
            response = Optional.ofNullable(restTemplate.postForObject(validationApiUrl + "/services/rest/validation/getOriginalDocuments", dto, FileDto[].class));
        } catch (HttpServerErrorException e) {
            if(!Objects.equals(e.getMessage(), "Signature Id cannot be null!")) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public Object validateCertificate(File signedFile, File originalFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile, originalFile);
        return restTemplate.postForObject(validationApiUrl + "/services/rest/validation/validateSignature", dto, Object.class);
    }
}
