package com.tiktokshop.exception;

public class TiktokShopApiException extends RuntimeException {

    private final int statusCode;

    public TiktokShopApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public TiktokShopApiException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
