package pl.hackyeah.szczepans.opener.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pl.hackyeah.szczepans.opener.controller.dto.FileDto;
import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.exception.FileStorageException;
import pl.hackyeah.szczepans.opener.model.Document;
import pl.hackyeah.szczepans.opener.properties.FileStorageProperties;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	private final DocumentDAO dao;
	
	
	public FileStorageService(FileStorageProperties fileStorageProperties, DocumentDAO dao) {
        this.dao = dao;
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	
	public Path storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
//	public Document saveFile(File file) {
//		return dao.save(new Document(file.getName(), file.getAbsolutePath()));
//	}
	
	public Document saveDocument(Document document) {
		return dao.save(document);
	}
	
	public byte[] downloadFile(Integer id) throws IOException {
		Optional<Document> document = dao.findById(id);
		if(document.isEmpty()) {
			return null;
		}
		return Files.readAllBytes(Path.of(document.get().getPath()));
	}
	
    public Path storeFile(FileDto fileDto) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(fileDto.getName());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(new ByteArrayInputStream(fileDto.getBytes()), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
