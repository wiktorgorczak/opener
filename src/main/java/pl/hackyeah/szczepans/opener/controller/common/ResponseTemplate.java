package pl.hackyeah.szczepans.opener.controller.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseTemplate<T> {

    @JsonProperty
    private Integer status;

    @JsonProperty
    private String message;

    @JsonProperty
    private T payload;

    public ResponseTemplate(Integer status, String message, T payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
