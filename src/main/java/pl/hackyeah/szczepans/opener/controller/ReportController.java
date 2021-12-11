package pl.hackyeah.szczepans.opener.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.hackyeah.szczepans.opener.controller.dto.ReportDTO;
import pl.hackyeah.szczepans.opener.controller.dto.ResponseTemplate;
import pl.hackyeah.szczepans.opener.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTemplate<ReportDTO>> generateReportForOne(@PathVariable Integer id) {
        ReportDTO body = reportService.generateReportForOne(id);
        return new ResponseEntity<>(ResponseTemplate.success(HttpStatus.OK.value(), body), HttpStatus.OK);
    }
    
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTemplate<List<ReportDTO>>> generateReportForAll() {
        List<ReportDTO> body = reportService.generateReportForAll();
        return new ResponseEntity<>(ResponseTemplate.success(HttpStatus.OK.value(), body), HttpStatus.OK);
    }
    
    @GetMapping(value = "/many", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTemplate<List<ReportDTO>>> generateReportForMany(@RequestParam List<Integer> ids) {
        List<ReportDTO> body = reportService.generateReportForMany(ids);
        return new ResponseEntity<>(ResponseTemplate.success(HttpStatus.OK.value(), body), HttpStatus.OK);
    }
}
