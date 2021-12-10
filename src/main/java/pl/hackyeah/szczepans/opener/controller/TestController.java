package pl.hackyeah.szczepans.opener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hackyeah.szczepans.opener.controller.common.ResponseTemplate;
import pl.hackyeah.szczepans.opener.controller.common.ServerResponseFactory;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<ResponseTemplate<String>> testEndpoint() {
        return ServerResponseFactory.createSuccessResponse("Hello World!");
    }
}
