package pl.hackyeah.szczepans.opener.service;

import org.springframework.stereotype.Service;

import pl.hackyeah.szczepans.opener.controller.dto.FileDto;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        File signedFile = fileStorageService.getFileFromPath(signedFilePath);
        Optional<FileDto[]> originalFileDto = certificateValidationClient.getOriginalDocument(signedFile);
        Map<String, Object> body = new HashMap<>();
        if (originalFileDto.isEmpty()) {
            body.put("signatureFound", false);
            return body;
        }
        String originalFilePath = fileStorageService.storeFile(originalFileDto.get()[0]);
        File originalFile = new File(originalFilePath);
        Object response = certificateValidationClient.validateCertificate(signedFile, originalFile);
        body.put("signatureFound", true);
        body.put("originalFilePath", originalFilePath);
        body.put("validationResult", response);
        return body;
    }
}
