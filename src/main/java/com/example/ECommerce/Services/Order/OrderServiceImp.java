package com.example.ECommerce.Services.Order;


import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DAOs.User.UserEntity;
import com.example.ECommerce.DTOs.Order.OrderDTO;
import com.example.ECommerce.DTOs.Order.OrderDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.OrderRepository;
import com.example.ECommerce.Services.Product.ProductService;
import com.example.ECommerce.Services.User.UserEntityService;
import com.example.ECommerce.Utility.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp  implements  OrderService{


    private final OrderRepository orderRepository;
    private final OrderDTOMapper orderDTOMapper;
    private final UserEntityService userEntityService;
    private final ProductService productService;

    public OrderServiceImp(OrderRepository orderRepository, OrderDTOMapper orderDTOMapper, UserEntityService userEntityService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderDTOMapper = orderDTOMapper;
        this.userEntityService = userEntityService;
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Object> fetchOrderById(long orderId) {
        final Order existingOrder = getOrderById(orderId);
        final OrderDTO order = orderDTOMapper.apply(existingOrder);

        return ResponseHandler.generateResponse(order , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchAllOrders(long pageNumber) {
        final Pageable pageable  = PageRequest.of((int)pageNumber - 1, 10 );
        final List<OrderDTO> orders = orderRepository.fetchAllOrders(pageable).stream().map(orderDTOMapper).toList();

        if(orders.isEmpty() && pageNumber > 1)
        {
            return fetchAllOrders(1);
        }
        final long total = orderRepository.getTotalOrderCount();

        return ResponseHandler.generateResponse(orders,HttpStatus.OK,orders.size(),total);

    }

    @Override
    public ResponseEntity<Object> fetchOrdersOfUser(final UUID userId, final long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber-1 , 10);
        final List<OrderDTO> orders = orderRepository.fetchOrdersFromUser(userId , pageable).stream().map(orderDTOMapper).toList();
        if(orders.isEmpty() && pageNumber > 1)
        {
            return fetchOrdersOfUser(userId , 1);
        }
        final long total = orderRepository.getTotalOrderCountByUser(userId);

        return ResponseHandler.generateResponse(orders , HttpStatus.OK,orders.size() , total);
    }

    @Override
    public ResponseEntity<Object> placeOrder(@NotNull UserDetails userDetails, @NotNull String orderJson) {
         return null;
    }

    @Override
    public ResponseEntity<Object> finishOrder(final long orderId) {
        return null;
    }

    @Override
    public Order getOrderById(final long orderId)
    {
        return orderRepository.fetchOrderById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Order with ID : %d could not be found in our system." , orderId))
        );
    }
}
