package pl.hackyeah.szczepans.opener.model;

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

	public Document() {	}

	public Document(String name, String path) {
		this.name = name;
		this.path = path;
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
}
