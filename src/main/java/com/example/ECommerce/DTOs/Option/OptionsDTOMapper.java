package com.example.ECommerce.DTOs.Option;

import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OptionsDTOMapper implements Function<Option, OptionDTO> {

    private final ProductDTOMapper productDTOMapper;

    public OptionsDTOMapper(ProductDTOMapper productDTOMapper) {
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    public OptionDTO apply(Option option) {
        return new OptionDTO(
                option.getId(),
                option.getTitle(),
                option.getDescription(),
                productDTOMapper.apply(option.getProduct())
        );
    }
}
