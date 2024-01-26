package com.example.ECommerce.DTOs.Product;

import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DTOs.Option.OptionsDTOMapper;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new  ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity(),
                product.getReference(),
                product.getLayoutDescription(),
                product.getOptions().stream().map(new OptionsDTOMapper()).toList(),
                new SubCategoryDTOMapper().apply(product.getSubCategory())
        );
    }
}
