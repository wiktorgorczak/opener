package pl.hackyeah.szczepans.opener.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseTemplate<T> {

    @JsonProperty
    private Integer status;

    @JsonProperty
    private String messageCode;

    @JsonProperty
    private T payload;

    public ResponseTemplate(Integer status, String messageCode, T payload) {
        this.status = status;
        this.messageCode = messageCode;
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
    
    public static <T> ResponseTemplate<T> success(Integer status, T payload) {
    	return new ResponseTemplate<T>(status, "success", payload);
    }
    
    public static <T> ResponseTemplate<T> error(Integer status, String message) {
    	return new ResponseTemplate<T>(status, message, null);
    }
}
