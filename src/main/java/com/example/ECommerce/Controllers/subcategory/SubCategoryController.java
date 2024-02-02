package com.example.ECommerce.Controllers.subcategory;


import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTO;
import com.example.ECommerce.Services.SubCategory.SubCategoryService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PutMapping("/{subcategoryId}")
    public ResponseEntity<Object> updateSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @NotNull @Valid @RequestBody SubCategory subCategory)
    {
        return subCategoryService.updateSubCategory(subcategoryId , subCategory);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Object> deleteSubCategory(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.deleteSubCategory(subcategoryId);
    }

    @GetMapping()
    public ResponseEntity<Object> fetchAllSubCategory()
    {
        return subCategoryService.fetchAllSubCategory();
    }

    @GetMapping("/{subcategoryId}")
    public ResponseEntity<Object> fetchSubCategoryById(@PathVariable("subcategoryId") final long subcategoryId)
    {
        return subCategoryService.fetchSubCategoryById(subcategoryId);
    }

    @GetMapping("/{subcategoryId}/products")
    public ResponseEntity<Object> fetchProductFromSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @RequestParam(value = "pageNumber" ,required = true) final long pageNumber
        )
    {
        return subCategoryService.fetchProductsFromSubCategory(subcategoryId , pageNumber);
    }

    @PutMapping("/{subcategoryId}/products")
    public ResponseEntity<Object>  addProductToSubCategory(
            @PathVariable("subcategoryId") final long subcategoryId,
            @RequestParam("multipartFiles") List<MultipartFile> multipartFiles,
            @RequestParam(value = "productJson" , required = true) final String productJson
    ) throws IOException {
        return subCategoryService.addProductToSubCategoryById(subcategoryId,multipartFiles,productJson);
    }
}
