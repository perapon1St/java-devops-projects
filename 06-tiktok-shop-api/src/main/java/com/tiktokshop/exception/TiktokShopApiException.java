package com.tiktokshop.exception;

import org.springframework.http.HttpStatus;

public class TiktokShopApiException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public TiktokShopApiException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public TiktokShopApiException(String message, HttpStatus status) {
        this(message, status, "TIKTOK_API_ERROR");
    }

    public TiktokShopApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
        this.errorCode = "TIKTOK_API_ERROR";
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
