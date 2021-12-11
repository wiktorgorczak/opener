package pl.hackyeah.szczepans.opener.controller.dto;

import pl.hackyeah.szczepans.opener.model.Document;

public class DocumentDTO {
	private Integer id;
	private String name;	
	private String type;
	
	public DocumentDTO() { }
	
	public DocumentDTO(Document document, String type) {
		this.id = document.getId();
		this.name = document.getName();		
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
