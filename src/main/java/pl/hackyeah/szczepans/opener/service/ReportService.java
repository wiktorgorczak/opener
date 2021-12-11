package pl.hackyeah.szczepans.opener.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.hackyeah.szczepans.opener.controller.dto.ReportDTO;
import pl.hackyeah.szczepans.opener.controller.dto.VerifyResultDto;
import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.dao.exception.FileStorageException;
import pl.hackyeah.szczepans.opener.model.Document;
import pl.hackyeah.szczepans.opener.properties.FileStorageProperties;

@Service
public class ReportService {
	private final Path fileStorageLocation;
	private final DocumentDAO dao;
	private final CertificateValidationService certificateValidationService;
	private final MediaTypeProgramMapper mediaTypeProgramMapper;
	
	public ReportService(DocumentDAO dao,
						 CertificateValidationService certificateValidationService,
						 MediaTypeProgramMapper mediaTypeProgramMapper,
						 FileStorageProperties fileStorageProperties) {
		this.dao = dao;
		this.certificateValidationService = certificateValidationService;
		this.mediaTypeProgramMapper = mediaTypeProgramMapper;
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(Path.of(fileStorageLocation.toString(), "/reports/"));
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
	}
	
	public ReportDTO generateReportForOne(Integer id) {
		Optional<Document> documentWrapped = dao.findById(id);
		if(documentWrapped.isEmpty()) {
			return null;
		}
		return generateReport(documentWrapped.get());
	}
	
	public List<ReportDTO> generateReportForAll() {
		List<ReportDTO> reports = new ArrayList<>();
		for(Document doc : dao.findAll()) {
			reports.add(generateReport(doc));
		}
		return reports;
	}
	
	public List<ReportDTO> generateReportForMany(List<Integer> ids) {
		List<ReportDTO> reports = new ArrayList<>();
		for(Document doc : dao.findAllById(ids)) {
			reports.add(generateReport(doc));
		}
		return reports;
	}
	
	private ReportDTO generateReport(Document document) {
		ReportDTO dto = new ReportDTO(document);
		VerifyResultDto validationResult = certificateValidationService.validate(document.getId());
		dto.setValidationResult(validationResult.getValidationResult());
		dto.setVerified(validationResult.getSignatureFound());
		String program = mediaTypeProgramMapper.getProgramByMediaType(document.getType());
		dto.setProgram(program);
		return dto;
	}
	
	public byte[] reportForOnePDF(Integer id) throws IOException {		
		ObjectMapper mapper = new ObjectMapper();	
		mapper.findAndRegisterModules();
		UUID uuid = UUID.randomUUID();
		String pathToFile = fileStorageLocation.toString() + "/reports/" + uuid.toString() + ".pdf";
		Files.createFile(Path.of(pathToFile));
		File file = new File(pathToFile);
		JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);
		generator.writeObject(generateReportForOne(id));
		generator.close();		
		return Files.readAllBytes(Path.of(pathToFile));
	}
	
	public byte[] reportForManyPDF(List<Integer> ids) throws IOException {		
		ObjectMapper mapper = new ObjectMapper();	
		mapper.findAndRegisterModules();
		UUID uuid = UUID.randomUUID();
		String pathToFile = fileStorageLocation.toString() + "/reports/" + uuid.toString() + ".pdf";
		Files.createFile(Path.of(pathToFile));
		File file = new File(pathToFile);
		JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);
		generator.writeObject(generateReportForMany(ids));
		generator.close();		
		return Files.readAllBytes(Path.of(pathToFile));
	}
	
	public byte[] reportForAllPDF() throws IOException {
		generateReportForAll();
		ObjectMapper mapper = new ObjectMapper();	
		mapper.findAndRegisterModules();
		UUID uuid = UUID.randomUUID();
		String pathToFile = fileStorageLocation.toString() + "/reports/" + uuid.toString() + ".pdf";
		Files.createFile(Path.of(pathToFile));
		File file = new File(pathToFile);
		JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);
		generator.writeObject(generateReportForAll());
		generator.close();		
		return Files.readAllBytes(Path.of(pathToFile));
	}
	
}
