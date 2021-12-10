package pl.hackyeah.szczepans.opener.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.hackyeah.szczepans.opener.service.FileStorageService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	private final FileStorageService fileStorageService;
	
	public FileController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()                
                .path(fileName)
                .toUriString();
        
        Map<String, String> body = new HashMap<>();
        body.put("path", fileDownloadUri);
        body.put("content-type", file.getContentType());
        
        return new ResponseEntity<Map<String,String>>(body, HttpStatus.CREATED);
	}
}
