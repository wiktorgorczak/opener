package pl.hackyeah.szczepans.opener.service;

import org.springframework.stereotype.Service;
import pl.hackyeah.szczepans.opener.controller.common.FileDto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class CertificateValidationService {

    private final CertificateValidationClient certificateValidationClient;
    private final FileStorageService fileStorageService;

    public CertificateValidationService(CertificateValidationClient certificateValidationClient,
                                        FileStorageService fileStorageService) {
        this.certificateValidationClient = certificateValidationClient;
        this.fileStorageService = fileStorageService;
    }

    public Map<String, Object> validate(String signedFilePath) {
        File signedFile = new File(signedFilePath);
        FileDto originalFileDto = certificateValidationClient.getOriginalDocument(signedFile);
        String originalFilePath = fileStorageService.storeFile(originalFileDto);
        File originalFile = new File(originalFilePath);
        Object response = certificateValidationClient.validateCertificate(signedFile, originalFile);
        Map<String, Object> body = new HashMap<>();
        body.put("originalFilePath", originalFilePath);
        body.put("validationResult", response);
        return body;
    }
}
