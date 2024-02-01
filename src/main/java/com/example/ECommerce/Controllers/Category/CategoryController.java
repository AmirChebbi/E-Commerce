package com.example.ECommerce.Controllers.Category;


import com.example.ECommerce.DAOs.Category.Category;
import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import com.example.ECommerce.DTOs.Category.CategoryDTO;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTO;
import com.example.ECommerce.Services.Category.CategoryService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@NotNull @Valid @RequestBody final CategoryDTO categoryDTO)
    {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateCategory(@PathVariable("categoryId")final long categoryId, @NotNull @Valid @RequestBody final Category categoryDetails)
    {
        return categoryService.updateCategory(categoryId , categoryDetails);
    }

    @PutMapping("/{categoryId}/subcategories")
    public ResponseEntity<Object> addSubCategory(@PathVariable("categoryId") final long categoryId , @NotNull @Valid @RequestBody final SubCategory subCategory)
    {
        return categoryService.addSubCategory(categoryId , subCategory);
    }

    @PutMapping("/{categoryId}/subcategories/{subCategoryId}")
    public ResponseEntity<Object> removeSubCategory(@PathVariable("categoryId") final long categoryId, @PathVariable("subCategoryId") final long subCategoryId)
    {
        return categoryService.removeSubCategory(categoryId,subCategoryId);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> fetchCategoryById(@PathVariable("categoryId") final long categoryId)
    {
        return categoryService.fetchCategoryById(categoryId);
    }

    @GetMapping()
    public ResponseEntity<Object> fetchAllCategories()
    {
        return categoryService.fetchAllCategories();
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<Object> fetchAllSubCategoryInCategoryById(@PathVariable("categoryId") final long categoryId)
    {
        return categoryService.fetchAllSubCategoryInCategoryById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId")final long categoryId)
    {
        return categoryService.deleteCategoryById(categoryId);
    }

}
