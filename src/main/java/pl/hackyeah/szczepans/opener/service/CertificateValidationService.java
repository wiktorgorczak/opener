package pl.hackyeah.szczepans.opener.service;

import org.springframework.stereotype.Service;
import pl.hackyeah.szczepans.opener.controller.dto.FileDto;
import pl.hackyeah.szczepans.opener.controller.dto.VerifyResultDto;
import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.model.Document;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CertificateValidationService {

    private final CertificateValidationClient certificateValidationClient;
    private final FileStorageService fileStorageService;
    private final DocumentDAO documentDAO;

    public CertificateValidationService(CertificateValidationClient certificateValidationClient,
                                        FileStorageService fileStorageService,
                                        DocumentDAO documentDAO) {
        this.certificateValidationClient = certificateValidationClient;
        this.fileStorageService = fileStorageService;
        this.documentDAO = documentDAO;
    }

    public VerifyResultDto validate(int fileId) {
        Optional<Document> documentOptional = documentDAO.findById(fileId);
        if (documentOptional.isEmpty()) {
            return null;
        }
        Document document = documentOptional.get();
        File signedFile = Path.of(document.getPath()).toFile();
        Optional<FileDto> unsignedFileDto = certificateValidationClient.getOriginalDocument(signedFile);
        if (unsignedFileDto.isEmpty()) {
            return new VerifyResultDto(false);
        }
        Path pathToUnsignedFile = fileStorageService.storeFile(unsignedFileDto.get());
        File unsignedFile = pathToUnsignedFile.toFile();
        document.setPathToUnsignedFile(pathToUnsignedFile.toString());
        document.setVerified(true);
        documentDAO.save(document);
        Object response = certificateValidationClient.validateCertificate(signedFile, unsignedFile);
        return new VerifyResultDto(true, response);
    }
}
