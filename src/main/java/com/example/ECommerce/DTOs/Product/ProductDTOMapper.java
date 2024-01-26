package com.example.ECommerce.DTOs.Product;

import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DTOs.Option.OptionsDTOMapper;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    private final OptionsDTOMapper optionsDTOMapper;
    private final SubCategoryDTOMapper subCategoryDTOMapper;
    public ProductDTOMapper(OptionsDTOMapper optionsDTOMapper, SubCategoryDTOMapper subCategoryDTOMapper) {
        this.optionsDTOMapper = optionsDTOMapper;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
    }

    @Override
    public ProductDTO apply(Product product) {
        return new  ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity(),
                product.getReference(),
                product.getLayoutDescription(),
                product.getOptions().stream().map(optionsDTOMapper).toList(),
                subCategoryDTOMapper.apply(product.getSubCategory())
        );
    }
}
