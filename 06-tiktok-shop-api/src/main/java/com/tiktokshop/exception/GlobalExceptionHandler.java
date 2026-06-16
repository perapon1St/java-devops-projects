package com.tiktokshop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.LinkedHashMap;
=======
import java.util.HashMap;
>>>>>>> origin/main
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(ProductNotFoundException ex) {
        log.warn("Product not found: {}", ex.getMessage());
<<<<<<< HEAD
        return buildErrorResponse(ex.getStatus(), ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(TiktokShopApiException.class)
    public ResponseEntity<Map<String, Object>> handleTiktokApiException(TiktokShopApiException ex) {
        log.error("TikTok Shop API error: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getStatus(), ex.getErrorCode(), ex.getMessage());
=======
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TiktokShopApiException.class)
    public ResponseEntity<Map<String, Object>> handleTiktokShopApiException(TiktokShopApiException ex) {
        log.error("TikTok Shop API error [{}]: {}", ex.getStatusCode(), ex.getMessage());
        HttpStatus status = ex.getStatusCode() == 429
                ? HttpStatus.TOO_MANY_REQUESTS
                : HttpStatus.BAD_GATEWAY;
        return buildErrorResponse(status, ex.getMessage());
>>>>>>> origin/main
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParam(MissingServletRequestParameterException ex) {
<<<<<<< HEAD
        log.warn("Missing required parameter: {}", ex.getParameterName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "MISSING_PARAMETER",
                "Required parameter '" + ex.getParameterName() + "' is missing");
=======
        log.warn("Missing request parameter: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Invalid request: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
>>>>>>> origin/main
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
<<<<<<< HEAD
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "An unexpected error occurred");
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String errorCode, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", errorCode);
=======
        log.error("Unexpected error", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
>>>>>>> origin/main
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
