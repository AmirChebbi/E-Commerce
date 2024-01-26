package com.example.ECommerce.DTOs.Cart;


import com.example.ECommerce.DTOs.Order.OrderDTO;

import java.util.List;
import java.util.UUID;

public record CartDTO (
    long id,
    UUID userId,
    List<OrderDTO> orders,
    float totalPrice
){
}
