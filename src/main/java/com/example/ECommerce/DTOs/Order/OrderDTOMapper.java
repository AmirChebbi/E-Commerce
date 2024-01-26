package com.example.ECommerce.DTOs.Order;

import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DTOs.Option.OptionsDTOMapper;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {

    private final ProductDTOMapper productDTOMapper;
    private final OptionsDTOMapper optionsDTOMapper;

    public OrderDTOMapper(ProductDTOMapper productDTOMapper, OptionsDTOMapper optionsDTOMapper) {
        this.productDTOMapper = productDTOMapper;
        this.optionsDTOMapper = optionsDTOMapper;
    }

    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getAddress(),
                order.getPrice(),
                productDTOMapper.apply(order.getProduct()),
                order.getOptions().stream().map(optionsDTOMapper).toList(),
                order.getCart().getId(),
                order.getUser().getId()
        );
    }
}
