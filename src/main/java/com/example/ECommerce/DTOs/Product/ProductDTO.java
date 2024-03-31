package com.example.ECommerce.DTOs.Product;

import com.example.ECommerce.DTOs.File.FileDataDTO;
import com.example.ECommerce.DTOs.Option.OptionDTO;


import java.util.List;


public record ProductDTO (
        long id,
        String title ,
        float price,
        String layoutDescription,
        String reference,
        List<OptionDTO> options,
        List<FileDataDTO> files
){
}
