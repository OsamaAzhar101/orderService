package com.oasys.OrderService.external.client.model;

import com.oasys.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private long orderId;
    private long amount;
    private PaymentMode paymentMode;
    private String referenceNumber;


}
