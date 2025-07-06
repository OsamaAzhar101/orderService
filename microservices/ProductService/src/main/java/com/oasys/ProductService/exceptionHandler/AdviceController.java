package com.oasys.ProductService.exceptionHandler;


import com.oasys.ProductService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        return new ResponseEntity<>
                (ErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .errormessage(ex.getMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        return new ResponseEntity<>
                (ErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .errormessage(ex.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>
                (ErrorResponse.builder()
                        .errorCode(ex.getErrorCode())
                        .errormessage(ex.getMessage())
                        .build(), HttpStatus.NOT_FOUND);



    }

    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>
                (ErrorResponse.builder()
                        .errorCode(ex.getErrorCode())
                        .errormessage(ex.getMessage())
                        .build(), HttpStatus.NOT_FOUND);
    }
}
