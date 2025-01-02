package de.bertin.ecommerce.exceptions;

public class ProductPurchaseException extends RuntimeException{
    private final String message;
    public ProductPurchaseException(String message) {
       this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
