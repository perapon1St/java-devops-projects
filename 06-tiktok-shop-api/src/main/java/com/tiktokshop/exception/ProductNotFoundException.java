package com.tiktokshop.exception;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends TiktokShopApiException {

    public ProductNotFoundException(String productId) {
        super("Product not found: " + productId, HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
=======
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productId) {
        super("Product not found with id: " + productId);
>>>>>>> origin/main
    }
}
