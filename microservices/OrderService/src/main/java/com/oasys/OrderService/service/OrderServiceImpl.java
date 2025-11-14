package com.oasys.OrderService.service;

import com.oasys.OrderService.entity.Order;
import com.oasys.OrderService.exception.CustomException;


import com.oasys.common_module.clients.external.model.OrderRequest;
import com.oasys.common_module.clients.external.model.OrderResponse;
import com.oasys.OrderService.repository.OrderRepository;
import com.oasys.common_module.clients.external.PaymentService;
import com.oasys.common_module.clients.external.ProductService;
import com.oasys.common_module.clients.external.model.PaymentRequest;
import com.oasys.common_module.clients.external.model.PaymentResponse;
import com.oasys.common_module.clients.external.model.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public Long createOrder(OrderRequest orderRequest) {

        log.info("Received order request: {}", orderRequest);
        productService.reduceQuantity(
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );
        log.info("Creating order with request: {}", orderRequest);
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .price(orderRequest.getPrice())
                .orderStatus("CREATED")

                .build();

        Order order1 = orderRepository.save(order);

        log.info("Order created with ID: {}", order1.getOrderId());

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order1.getOrderId())
                .amount(orderRequest.getPrice())
                .paymentMode(orderRequest.getPaymentMode())

                .build();


        try {
            paymentService.processPayment(paymentRequest);
            log.info("Payment processed successfully for Order ID: {}", order1.getOrderId());
            order1.setOrderStatus("ORDER_PLACED");
        } catch (Exception e) {
            log.error("Payment processing failed for Order ID: {}", order1.getOrderId(), e);
            order1.setOrderStatus("PAYMENT_FAILED");
            orderRepository.save(order1);
            throw new RuntimeException("Payment processing failed, order status updated to PAYMENT_FAILED");
        }
        return order1.getOrderId();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        log.info("Fetching order with ID: {}", orderId);

        Order order =
                orderRepository.findById(orderId).orElseThrow(
                        () -> CustomException.builder()
                                .errorMessage("Order not found with ID: " + orderId)
                                .errorCode("ORDER_NOT_FOUND")
                                .build()
                );

        log.info("Order fetched successfully: {}", order);


        ResponseEntity<ProductResponse> productResponse =
                productService.getProductById(order.getProductId());

        ProductResponse productDetails = ProductResponse.builder()
                .productName(productResponse.getBody().getProductName())
                .price(productResponse.getBody().getPrice())
                .productId(productResponse.getBody().getProductId())
                .quantity(productResponse.getBody().getQuantity())
                .build();

        log.info("Product details fetched successfully for Order ID: {}", orderId);


        log.info("Getting Payment Response from Payment Service for Order ID: {}", orderId);

        ResponseEntity<PaymentResponse> paymentResponseResponseEntity =
                paymentService.getPaymentDetailsByOrderId(orderId);


        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentDate(paymentResponseResponseEntity.getBody().getPaymentDate())
                .paymentId(paymentResponseResponseEntity.getBody().getPaymentId())
                .paymentStatus(paymentResponseResponseEntity.getBody().getPaymentStatus())
                .amount(paymentResponseResponseEntity.getBody().getAmount())
                .orderId(paymentResponseResponseEntity.getBody().getOrderId())
                .paymentMode(paymentResponseResponseEntity.getBody().getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .orderId(order.getOrderId())
                .productDetails(productDetails)
                .paymentDetails(paymentResponse)
                .build();

    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

/*    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        if (existingOrder != null) {
            updatedOrder.setId(orderId);
            return orderRepository.save(updatedOrder);
        }
        return null;
    }*/

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}