package com.example.ECommerce.DTOs.Option;

import com.example.ECommerce.DTOs.Product.ProductDTO;

public record OptionDTO(
    long id,
    String title,
    String description,
    ProductDTO product
){
}
