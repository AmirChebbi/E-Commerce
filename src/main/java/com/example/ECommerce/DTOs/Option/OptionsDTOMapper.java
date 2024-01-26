package com.example.ECommerce.DTOs.Option;

import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OptionsDTOMapper implements Function<Option, OptionDTO> {

    @Override
    public OptionDTO apply(Option option) {
        return new OptionDTO(
                option.getId(),
                option.getTitle(),
                option.getDescription(),
                new ProductDTOMapper().apply(option.getProduct())
        );
    }
}
