package pl.hackyeah.szczepans.opener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
