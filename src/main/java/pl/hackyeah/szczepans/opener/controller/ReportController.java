package pl.hackyeah.szczepans.opener.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void generateReportForOne(@PathVariable Integer id) {
		
	}
}
