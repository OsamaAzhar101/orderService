package com.oasys.OrderService.config;

import com.oasys.OrderService.external.client.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class FeignClient {
    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
