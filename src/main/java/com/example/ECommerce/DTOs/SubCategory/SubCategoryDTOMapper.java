package com.example.ECommerce.DTOs.SubCategory;

import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubCategoryDTOMapper implements Function<SubCategory, SubCategoryDTO> {

    @Override
    public SubCategoryDTO apply(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getTitle(),
                subCategory.getProducts().stream().map(new ProductDTOMapper()).toList()
        );
    }
}
