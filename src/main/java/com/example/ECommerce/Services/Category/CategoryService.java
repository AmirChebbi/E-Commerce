package com.example.ECommerce.Services.Category;


import com.example.ECommerce.DAOs.Category.Category;
import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;


public interface CategoryService {

    ResponseEntity<Object> createCategory(@NotNull final Category category);
    ResponseEntity<Object> updateCategory(final long categoryId , @NotNull final Category category);
    ResponseEntity<Object> deleteCategoryById(final long categoryId);
    ResponseEntity<Object> addSubCategory(final long categoryId, @NotNull final SubCategory subCategory);
    ResponseEntity<Object> removeSubCategory(final long categoryId, final long subCategoryId) ;
    ResponseEntity<Object> fetchCategoryById(final long categoryId);
    ResponseEntity<Object> fetchAllCategories();
    ResponseEntity<Object> fetchAllSubCategoryInCategoryById(final long categoryId);
    public Category getCategoryById(final long categoryId);


}
