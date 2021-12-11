package pl.hackyeah.szczepans.opener.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.hackyeah.szczepans.opener.dao.DocumentDAO;
import pl.hackyeah.szczepans.opener.model.Document;

@Service
public class ReportService {
	
	private final DocumentDAO dao;
	
	public ReportService(DocumentDAO dao) {
		this.dao = dao;
	}
	
	public void generateRaportForOne(Integer id) {
		Optional<Document> documentWrapped = dao.findById(id);
		if(documentWrapped.isEmpty()) {
			return;
		}
		Document document = documentWrapped.get();
	}
	
}
