package com.example.ECommerce.Controllers.Order;

import com.example.ECommerce.DTOs.Order.OrderDTO;
import com.example.ECommerce.Services.Order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> fetchOrderById(@PathVariable("orderId") final long orderId)
    {
        return orderService.fetchOrderById(orderId);
    }

    @GetMapping()
    public ResponseEntity<Object> fetchAllOrders(@RequestParam(value = "pageNumber", required = true) final long pageNumber)
    {
        return orderService.fetchAllOrders(pageNumber);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> fetchOrdersOfUser(@PathVariable("userId") final UUID userId, @RequestParam(value = "pageNumber", required = true) final long pageNumber) {
        return orderService.fetchOrdersOfUser(userId, pageNumber);
    }

    @PostMapping()
    public ResponseEntity<Object> placeOrder(@AuthenticationPrincipal UserDetails userDetails , @RequestBody(required = true) OrderDTO orderDTO) throws JsonProcessingException {
        return orderService.placeOrder(userDetails , orderDTO);
    }

}


