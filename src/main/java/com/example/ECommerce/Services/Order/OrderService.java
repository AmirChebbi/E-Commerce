package com.example.ECommerce.Services.Order;

import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DTOs.Order.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    public ResponseEntity<Object> fetchOrderById(final long orderId);
    public ResponseEntity<Object> fetchAllOrders(final long pageNumber);
    public ResponseEntity<Object> fetchOrdersOfUser(final UUID userId, final long pageNumber);
    public  ResponseEntity<Object> placeOrder(@NotNull UserDetails userDetails,@NotNull final OrderDTO orderDTO) throws JsonProcessingException;
    public Order getOrderById(final long orderId);
    public List<Order> fetchAllCartOrders(long cartId);
}
