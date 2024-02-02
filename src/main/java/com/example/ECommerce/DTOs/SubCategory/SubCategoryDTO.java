package com.example.ECommerce.DTOs.SubCategory;

import com.example.ECommerce.DTOs.Product.ProductDTO;

import java.util.List;

public record SubCategoryDTO (
        long id,
        String title,
        List<ProductDTO> productDTOS
){
}
