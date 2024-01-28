package com.example.ECommerce.DTOs.Product;

import com.example.ECommerce.DTOs.Option.OptionDTO;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTO;


import java.util.List;


public record ProductDTO (
        long id,
        String title ,
        float price,
        String layoutDescription,
        String reference,
        List<OptionDTO> options,
        SubCategoryDTO subCategory
){
}
