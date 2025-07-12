package com.oasys.OrderService.service;

import com.oasys.OrderService.entity.Order;
import com.oasys.OrderService.exception.CustomException;
import com.oasys.OrderService.external.client.PaymentService;
import com.oasys.OrderService.external.client.model.PaymentRequest;
import com.oasys.OrderService.external.client.model.ProductResponse;
import com.oasys.OrderService.model.OrderRequest;
import com.oasys.OrderService.model.OrderResponse;
import com.oasys.OrderService.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.oasys.OrderService.external.client.ProductService;

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

        if (!productResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to fetch product details for Order ID: {}", orderId);
            throw new CustomException("Failed to fetch product details",
                    "PRODUCT_NOT_FOUND", productResponse.getStatusCode().value());
        }

        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .orderId(order.getOrderId())
                .productDetails(productResponse.getBody())
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