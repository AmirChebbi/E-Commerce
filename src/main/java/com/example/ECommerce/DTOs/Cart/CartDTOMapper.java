package com.example.ECommerce.DTOs.Cart;

import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DTOs.Order.OrderDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CartDTOMapper implements Function<Cart, CartDTO> {
    @Override
    public CartDTO apply(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getUserEntity().getId(),
                cart.getOrders().stream().map(new OrderDTOMapper()).toList(),
                cart.getTotalPrice()
        );
    }
}
