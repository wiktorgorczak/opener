package pl.hackyeah.szczepans.opener.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.hackyeah.szczepans.opener.controller.dto.DocumentDTO;
import pl.hackyeah.szczepans.opener.controller.dto.ResponseTemplate;
import pl.hackyeah.szczepans.opener.service.CertificateValidationService;
import pl.hackyeah.szczepans.opener.service.FileStorageService;
import pl.hackyeah.szczepans.opener.service.FileTypeDetector;

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
		for(MultipartFile file : files) {
			Path pathToFile = fileStorageService.storeFile(file);
			File createdFile = pathToFile.toFile();	       
	        
	        DocumentDTO dto = new DocumentDTO(fileStorageService.saveFile(createdFile), 
	        		fileTypeDetector.checkFileType(createdFile).orElse("unknown"));
	        body.add(dto);
		}
        
        return new ResponseEntity<ResponseTemplate<List<DocumentDTO>>>(ResponseTemplate.success(200, body), HttpStatus.CREATED);
	}

	@GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] downloadFile(@PathVariable Integer id) throws IOException {
		return fileStorageService.downloadFile(id);
	}
	
    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") String filePath) {
       Map<String, Object> body = certificateValidationService.validate(filePath);
       return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
