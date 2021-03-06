package pl.hackyeah.szczepans.opener.controller.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import pl.hackyeah.szczepans.opener.model.Document;

public class ReportDTO {
private static final ZoneId zoneId = ZoneId.systemDefault();
	
	private Integer id;
	private String name;	
	private String type;
	private String expectedSuffix;
	private String realSuffix;
	private Boolean verified;
	private ZonedDateTime uploadDate;
	private Long size;
	private Object validationResult;
	private String program;
	
	public ReportDTO() { }
	
	public ReportDTO(Document document) {
		this.id = document.getId();
		this.name = document.getName();		
		this.type = document.getType();
		this.expectedSuffix = document.getExpectedSuffix();
		this.realSuffix = document.getRealSuffix();
		this.verified = document.getVerified();
		this.uploadDate = ZonedDateTime.of(document.getUploadDate(), zoneId);
		this.size = document.getSize();
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
	public String getExpectedSuffix() {
		return expectedSuffix;
	}
	public void setExpectedSuffix(String expectedSuffix) {
		this.expectedSuffix = expectedSuffix;
	}
	public String getRealSuffix() {
		return realSuffix;
	}
	public void setRealSuffix(String realSuffix) {
		this.realSuffix = realSuffix;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public ZonedDateTime getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(ZonedDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Object getValidationResult() {
		return validationResult;
	}
	public void setValidationResult(Object validationResult) {
		this.validationResult = validationResult;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
}
