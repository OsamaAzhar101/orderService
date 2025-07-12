package com.oasys.OrderService.controller;

import com.oasys.OrderService.entity.Order;
import com.oasys.OrderService.model.OrderRequest;
import com.oasys.OrderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest   ) {

        log.info("Received order request: {}", orderRequest);

//        1 - Save order details in the database with order status as "CREATED"

       Long orderId =  orderService.createOrder(orderRequest);

       log.info("Order created successfully with ID: {}", orderId);

        // Return the order ID in the response
       return ResponseEntity.ok(orderId);
    }

}
