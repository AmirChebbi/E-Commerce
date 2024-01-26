package com.example.ECommerce.DTOs.Category;


import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTO;

import java.util.List;

public record CategoryDTO (
        long categoryId,
        String title,
        List<SubCategoryDTO> subCategories
){

}
