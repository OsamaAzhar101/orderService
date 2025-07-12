package com.oasys.OrderService.service;

import com.oasys.OrderService.entity.Order;
import com.oasys.OrderService.model.OrderRequest;
import com.oasys.OrderService.model.OrderResponse;

import java.util.List;

public interface OrderService {
    public Long createOrder(OrderRequest order);
    public OrderResponse getOrderById(Long orderId);
    public List<Order> getAllOrders();
//    public Order updateOrder(Long orderId, Order updatedOrder);
    public void deleteOrder(Long orderId);

}
