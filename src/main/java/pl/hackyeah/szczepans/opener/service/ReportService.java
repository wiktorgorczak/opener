package pl.hackyeah.szczepans.opener.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.hackyeah.szczepans.opener.controller.dto.ReportDTO;
import pl.hackyeah.szczepans.opener.controller.dto.VerifyResultDto;
import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.model.Document;

@Service
public class ReportService {
	
	private final DocumentDAO dao;
	private final CertificateValidationService certificateValidationService;
	private final MediaTypeProgramMapper mediaTypeProgramMapper;
	
	public ReportService(DocumentDAO dao,
						 CertificateValidationService certificateValidationService,
						 MediaTypeProgramMapper mediaTypeProgramMapper) {
		this.dao = dao;
		this.certificateValidationService = certificateValidationService;
		this.mediaTypeProgramMapper = mediaTypeProgramMapper;
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
	
}
