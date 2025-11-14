package com.oasys.OrderService.service;

import com.oasys.OrderService.entity.Order;
import com.oasys.common_module.clients.external.model.OrderRequest;
import com.oasys.common_module.clients.external.model.OrderResponse;


import java.util.List;

public interface OrderService {
    public Long createOrder(OrderRequest order);
    public OrderResponse getOrderById(Long orderId);
    public List<Order> getAllOrders();
//    public Order updateOrder(Long orderId, Order updatedOrder);
    public void deleteOrder(Long orderId);

}
