package com.example.ECommerce.Services.Options;

import com.example.ECommerce.DTOs.Option.OptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService{
    @Override
    public ResponseEntity<Object> addOption(OptionDTO optionDTO, long productId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteOptionById(long optionId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteProductOptions(long productId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateOption(OptionDTO optionDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getOptionById(long optionId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllOptions(long pageNumber) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllProductOptions(long pageNumber) {
        return null;
    }

    @Override
    public ResponseEntity<Object> addOptionToOrder(long optionId, long orderId) {
        return null;
    }
}
