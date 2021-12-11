package pl.hackyeah.szczepans.opener.controller.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServerResponseFactory {

    private ServerResponseFactory() {
    }

    public static <T> ResponseEntity<ResponseTemplate<T>> createSuccessResponse(T payload) {
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK.value(), "OK", payload));
    }
}
