package com.oasys.OrderService.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomException extends RuntimeException {
    private String errorMessage;
    private String errorCode;

    private int statusCode;

    public CustomException(String errorMessage, String errorCode, int statusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }


}
