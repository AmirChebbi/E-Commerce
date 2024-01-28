package com.example.ECommerce.DTOs.Order;

import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DTOs.Option.OptionDTOMapper;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {

    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getAddress(),
                order.getPrice(),
                new ProductDTOMapper().apply(order.getProduct()),
                order.getOptions().stream().map(new OptionDTOMapper()).toList(),
                order.getCart().getId(),
                order.getUser().getId()
        );
    }
}
