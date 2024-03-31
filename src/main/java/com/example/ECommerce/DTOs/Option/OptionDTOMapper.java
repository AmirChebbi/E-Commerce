package com.example.ECommerce.DTOs.Option;

import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OptionDTOMapper implements Function<Option, OptionDTO> {

    @Override
    public OptionDTO apply(Option option) {
        return new OptionDTO(
                option.getId(),
                option.getTitle(),
                option.getDescription()
        );
    }
}
