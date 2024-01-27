package com.example.ECommerce.Services.Options;

import com.example.ECommerce.DTOs.Option.OptionDTO;
import org.springframework.http.ResponseEntity;

public interface OptionService {

    public ResponseEntity<Object> addOption(OptionDTO optionDTO, long productId);
    public ResponseEntity<Object> deleteOptionById(long optionId);
    public ResponseEntity<Object> deleteProductOptions(long productId);
    public ResponseEntity<Object> updateOption(OptionDTO optionDTO);
    public ResponseEntity<Object> getOptionById(long optionId);
    public ResponseEntity<Object> getAllOptions(long pageNumber);
    public ResponseEntity<Object> getAllProductOptions(long pageNumber);
    public ResponseEntity<Object> addOptionToOrder(long optionId, long orderId);

}
