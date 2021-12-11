package pl.hackyeah.szczepans.opener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.hackyeah.szczepans.opener.controller.common.FileDto;
import pl.hackyeah.szczepans.opener.controller.common.ValidationApiDto;

import java.io.File;

@Component
public class CertificateValidationClient {

    private final String validationApiUrl;
    private final RestTemplate restTemplate;

    public CertificateValidationClient(@Value("${opener.certificate.url}") String validationApiUrl) {
        this.validationApiUrl = validationApiUrl;
        this.restTemplate = new RestTemplate();
    }

    public FileDto getOriginalDocument(File signedFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile);
        return restTemplate.postForObject(validationApiUrl + "/services/rest/validation/getOriginalDocuments", dto, FileDto.class);
    }

    public Object validateCertificate(File signedFile, File originalFile) {
        ValidationApiDto dto = new ValidationApiDto(signedFile, originalFile);
        return restTemplate.postForObject(validationApiUrl + "/services/rest/validation/validateSignature", dto, Object.class);
    }
}
