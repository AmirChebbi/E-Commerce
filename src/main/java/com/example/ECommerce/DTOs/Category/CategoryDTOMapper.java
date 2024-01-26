package com.example.ECommerce.DTOs.Category;

import com.example.ECommerce.DAOs.Category.Category;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class CategoryDTOMapper implements Function<Category,CategoryDTO > {

    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                Optional.ofNullable(category.getSubCategories()).map(subCategoriesList -> subCategoriesList.stream().map(new SubCategoryDTOMapper()).toList()).orElse(null));    }
}
