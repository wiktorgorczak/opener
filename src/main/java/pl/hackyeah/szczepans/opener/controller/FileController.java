package pl.hackyeah.szczepans.opener.controller;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.hackyeah.szczepans.opener.controller.dto.DocumentDTO;
import pl.hackyeah.szczepans.opener.controller.dto.ResponseTemplate;
import pl.hackyeah.szczepans.opener.controller.dto.VerifyResultDto;
import pl.hackyeah.szczepans.opener.model.Document;
import pl.hackyeah.szczepans.opener.service.CertificateValidationService;
import pl.hackyeah.szczepans.opener.service.FileStorageService;
import pl.hackyeah.szczepans.opener.service.FileTypeDetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileStorageService fileStorageService;
    private final FileTypeDetector fileTypeDetector;
    private final CertificateValidationService certificateValidationService;

    public FileController(FileStorageService fileStorageService,
                          FileTypeDetector fileTypeDetector,
                          CertificateValidationService certificateValidationService) {
        this.fileStorageService = fileStorageService;
        this.fileTypeDetector = fileTypeDetector;
        this.certificateValidationService = certificateValidationService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTemplate<List<DocumentDTO>>> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<DocumentDTO> body = new ArrayList<>();
        for (MultipartFile file : files) {
            Path pathToFile = fileStorageService.storeFile(file);
            File createdFile = pathToFile.toFile();
            
            Triple<String, String, String> detected = fileTypeDetector.checkFileType(createdFile);
            String type = detected.getLeft();
            String expectedSuffix = detected.getMiddle();
            String realSuffix = detected.getRight();                        
            
            Document document = new Document(createdFile.getName(), createdFile.getAbsolutePath(),
            		type, expectedSuffix, realSuffix, createdFile.length());
            
            DocumentDTO dto = new DocumentDTO(fileStorageService.saveDocument(document));           
            body.add(dto);
        }

        return new ResponseEntity<>(ResponseTemplate.success(HttpStatus.CREATED.value(), body), HttpStatus.CREATED);
    }

    @GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downloadFile(@PathVariable Integer id) throws IOException {
        return fileStorageService.downloadFile(id);
    }

    @PostMapping(value = "/verify/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTemplate<VerifyResultDto>> verify(@PathVariable Integer id) {
        VerifyResultDto body = certificateValidationService.validate(id);
        return new ResponseEntity<>(ResponseTemplate.success(200, body), HttpStatus.OK);
    }
}
