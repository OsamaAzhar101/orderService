package com.oasys.ProductService.exceptionHandler;

import lombok.Data;
import lombok.Getter;

@Getter
public class ProductServiceCustomException extends RuntimeException {
    private String errorCode;

    public ProductServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
