package pl.hackyeah.szczepans.opener.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "path", nullable = false, unique = true)
	private String path;

	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "expectedSuffix", nullable = true)
	private String expectedSuffix;
	
	@Column(name = "realSuffix", nullable = true)
	private String realSuffix;		
	
	@Column(name = "verified", nullable = false)
	private Boolean verified;
	
	@Column(name = "uploadDate", nullable = false)
	private LocalDateTime uploadDate;
	
	@Column(name = "pathToUnsignedFile", nullable = true)
	private String pathToUnsignedFile;
	
	@Column(name = "size", nullable = false)
	private Long size;
	
	public Document() {	}

	public Document(String name, String path, String type, String expectedSuffix, String realSuffix, long size) {
		this.name = name;
		this.path = path;
		this.type = type;		
		this.expectedSuffix = expectedSuffix;
		this.realSuffix = realSuffix;
		this.size = size;
		this.verified = false;
		this.uploadDate = LocalDateTime.now();
		this.pathToUnsignedFile = null;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRealSuffix() {
		return realSuffix;
	}

	public void setRealSuffix(String realSuffix) {
		this.realSuffix = realSuffix;
	}

	public String getExpectedSuffix() {
		return expectedSuffix;
	}

	public void setExpectedSuffix(String expectedSuffix) {
		this.expectedSuffix = expectedSuffix;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getPathToUnsignedFile() {
		return pathToUnsignedFile;
	}

	public void setPathToUnsignedFile(String pathToUnsignedFile) {
		this.pathToUnsignedFile = pathToUnsignedFile;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
}
