package com.oasys.OrderService.model;

import com.oasys.OrderService.external.client.model.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long orderId;

    private Instant orderDate;
    private long amount;

    private String orderStatus;

    private ProductResponse productDetails;

}
