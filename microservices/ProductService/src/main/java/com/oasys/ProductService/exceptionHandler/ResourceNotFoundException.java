package com.oasys.ProductService.exceptionHandler;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    String errorCode;

    public ResourceNotFoundException(String message, String errorCode) {

        super(message);
        this.errorCode = errorCode;
    }
}