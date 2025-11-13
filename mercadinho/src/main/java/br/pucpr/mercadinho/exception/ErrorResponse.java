package br.pucpr.mercadinho.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private Integer code;
    private String codeDescription;
    private String message;
    private String stackTrack;
    private LocalDateTime data;

    public ErrorResponse(Integer code, String codeDescription, String message, String stackTrack, LocalDateTime data) {
        this.code = code;
        this.codeDescription = codeDescription;
        this.message = message;
        this.stackTrack = stackTrack;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrack() {
        return stackTrack;
    }

    public void setStackTrack(String stackTrack) {
        this.stackTrack = stackTrack;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
