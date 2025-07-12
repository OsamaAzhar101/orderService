package com.oasys.OrderService.service;

import com.oasys.OrderService.entity.Order;
import com.oasys.OrderService.model.OrderRequest;
import com.oasys.OrderService.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        return order1.getOrderId();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
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