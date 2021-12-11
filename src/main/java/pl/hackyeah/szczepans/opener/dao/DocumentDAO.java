package pl.hackyeah.szczepans.opener.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.hackyeah.szczepans.opener.model.Document;

public interface DocumentDAO extends JpaRepository<Document, Integer> {

}
