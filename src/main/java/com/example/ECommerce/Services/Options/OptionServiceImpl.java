package com.example.ECommerce.Services.Options;

import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DTOs.Option.OptionDTO;
import com.example.ECommerce.DTOs.Option.OptionDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Exceptions.UnauthorizedActionException;
import com.example.ECommerce.Repositories.OptionRepository;
import com.example.ECommerce.Repositories.OrderRepository;
import com.example.ECommerce.Repositories.ProductRepository;
import com.example.ECommerce.Utility.ResponseHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OptionServiceImpl implements OptionService{
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final OptionDTOMapper optionDTOMapper;
    private final OrderRepository orderRepository;
    public OptionServiceImpl(ProductRepository productRepository, OptionRepository optionRepository, OptionDTOMapper optionDTOMapper, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.optionDTOMapper = optionDTOMapper;
        this.orderRepository = orderRepository;
    }
    @Override
    public ResponseEntity<Object> addOption(OptionDTO optionDTO, long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("The referenced product doesn't exist !!"));
        if(optionRepository.findOptionByTitleAndProduct(optionDTO.title(),productId).isEmpty()){
            Option option = new Option(
                    optionDTO.title(),
                    optionDTO.description(),
                    product
            );
            optionRepository.save(option);
            String successMessage = String.format("Option saved successfully !!");
            return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("Option already exists !!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteOptionById(long optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow(()-> new ResourceNotFoundException("Option already doesn't exist !!"));
        optionRepository.delete(option);
        String successMessage = String.format("Option deleted successfully !!");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteProductOptions(long productId) {
        List<Option> options = optionRepository.findAllProductOptions(productId);
        if (!options.isEmpty()){
            optionRepository.deleteAll(options);
            String successMessage = String.format("All options were deleted successfully ");
            return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("There are already no options to delete");
        }
    }

    @Override
    public ResponseEntity<Object> updateOption(OptionDTO optionDTO) {
        Option option = optionRepository.findById(optionDTO.id()).orElseThrow(()-> new ResourceNotFoundException("Option doesn't exist !!"));
        Product product = productRepository.findById(optionDTO.id()).orElseThrow(()-> new ResourceNotFoundException("Referenced product doesn't exist !!"));
        option.setProduct(product);
        option.setDescription(optionDTO.description());
        option.setTitle(optionDTO.title());
        optionRepository.save(option);
        String successMessage = String.format("Option saved successfully !!");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getOptionById(long optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow(()-> new ResourceNotFoundException("Option doesn't exist !!"));
        return ResponseHandler.generateResponse(optionDTOMapper.apply(option),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllOptions(long pageNumber) {
        Pageable pageable = PageRequest.of((int) pageNumber -1,5);
        List<Option> options = optionRepository.findAllPageable(pageable);
        int count = optionRepository.getCount();
        return ResponseHandler.generateResponse(options,HttpStatus.OK,options.size(),count);
    }

    @Override
    public ResponseEntity<Object> getAllProductOptions(long productId) {
        List<Option> options = optionRepository.findAllProductOptions(productId);
        return ResponseHandler.generateResponse(options,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addOptionToOrder(long optionId, long orderId) {
        Option option = optionRepository.findById(optionId).orElseThrow(() -> new ResourceNotFoundException("Option doesn't exist !!"));
        Order order = orderRepository.fetchOrderById(orderId).orElseThrow((()-> new ResourceNotFoundException("Order doesn't exist !!")));
        if (!order.getOptions().contains(option)){
            order.getOptions().add(option);
            orderRepository.save(order);
            String successMessage = String.format("Option added successfully");
            return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("Option already added");
        }
    }
}
