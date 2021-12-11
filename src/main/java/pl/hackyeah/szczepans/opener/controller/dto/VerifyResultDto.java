package pl.hackyeah.szczepans.opener.controller.dto;

public class VerifyResultDto {

    private Boolean signatureFound;
    private Object validationResult;

    public VerifyResultDto() {
    }

    public VerifyResultDto(Boolean signatureFound) {
        this.signatureFound = signatureFound;
    }

    public VerifyResultDto(Boolean signatureFound, Object validationResult) {
        this.signatureFound = signatureFound;
        this.validationResult = validationResult;
    }

    public Boolean getSignatureFound() {
        return signatureFound;
    }

    public void setSignatureFound(Boolean signatureFound) {
        this.signatureFound = signatureFound;
    }

    public Object getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(Object validationResult) {
        this.validationResult = validationResult;
    }
}
