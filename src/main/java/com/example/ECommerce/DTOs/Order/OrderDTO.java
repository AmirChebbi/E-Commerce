package com.example.ECommerce.DTOs.Order;


import com.example.ECommerce.DTOs.Option.OptionDTO;
import com.example.ECommerce.DTOs.Product.ProductDTO;

import java.util.List;
import java.util.UUID;


public record OrderDTO(
        long id,
        String address,
        float price,
        ProductDTO product,
        List<OptionDTO> options,
        long cartId,
        UUID userId
){
}
