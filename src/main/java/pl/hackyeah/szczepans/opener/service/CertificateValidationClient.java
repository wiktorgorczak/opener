package pl.hackyeah.szczepans.opener.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.hackyeah.szczepans.opener.controller.dto.FileDto;
import pl.hackyeah.szczepans.opener.controller.dto.ValidationApiDto;
import pl.hackyeah.szczepans.opener.properties.ValidationApiProperties;

import java.io.File;
import java.util.Optional;

@Component
public class CertificateValidationClient {

    private final String validationApiUrl;
    private final RestTemplate restTemplate;

    public CertificateValidationClient(ValidationApiProperties validationApiProperties) {
        this.validationApiUrl = validationApiProperties.getHost();
        this.restTemplate = new RestTemplate();
    }

    public Optional<FileDto> getOriginalDocument(File signedFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile);
        Optional<FileDto> response = Optional.empty();
        try {
            response = Optional.ofNullable(restTemplate.postForObject(validationApiUrl + "/services/rest/validation/getOriginalDocuments", dto, FileDto[].class))
                               .map(res -> res[0]);
        } catch (HttpServerErrorException e) {
            // can't check signature
        }
        return response;
    }

    public Object validateCertificate(File signedFile, File originalFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile, originalFile);
        return restTemplate.postForObject(validationApiUrl + "/services/rest/validation/validateSignature", dto, Object.class);
    }
}
