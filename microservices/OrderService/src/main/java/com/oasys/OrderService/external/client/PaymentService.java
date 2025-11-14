/*
package com.oasys.OrderService.external.client;

import com.oasys.OrderService.external.client.model.PaymentRequest;
import com.oasys.OrderService.external.client.model.PaymentResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) ;

    @GetMapping("/response/{oderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId
            (@PathVariable long orderId);


}
*/
