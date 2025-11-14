/*
package com.oasys.OrderService.external.client.decoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.OrderService.exception.CustomException;
import com.oasys.OrderService.external.client.model.ErrorResponse;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, feign.Response response) {


        ObjectMapper objectMapper = new ObjectMapper();

        log.error("Error occurred in method: {}, status: {}, body: {}",
                methodKey, response.status(), response.body());

        try {
            ErrorResponse errorResponse = objectMapper.
                    readValue(response.body().asInputStream(), ErrorResponse.class);

        return new CustomException(
                errorResponse.getErrorMessage(),
                errorResponse.getErrorCode(),
                response.status()
        );

        } catch (IOException e) {
            throw new CustomException(
                    "Failed to parse error response",
                    "PARSE_ERROR",
                    response.status()
            );
        }


    }
}
*/
