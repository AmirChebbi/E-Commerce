package com.example.ECommerce.Services.Order;


import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DTOs.Order.OrderDTO;
import com.example.ECommerce.DTOs.Order.OrderDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Exceptions.UnauthorizedActionException;
import com.example.ECommerce.Repositories.CartRepository;
import com.example.ECommerce.Repositories.OrderRepository;
import com.example.ECommerce.Services.Cart.CartService;
import com.example.ECommerce.Services.Options.OptionService;
import com.example.ECommerce.Services.Product.ProductService;
import com.example.ECommerce.Services.User.UserEntityService;
import com.example.ECommerce.Utility.ResponseHandler;
import org.aspectj.weaver.ast.Or;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp  implements  OrderService{
    private final OptionService optionService;
    private final OrderRepository orderRepository;
    private final OrderDTOMapper orderDTOMapper;
    private final UserEntityService userEntityService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public OrderServiceImp(OptionService optionService, OrderRepository orderRepository, OrderDTOMapper orderDTOMapper, UserEntityService userEntityService, ProductService productService, CartRepository cartRepository) {
        this.optionService = optionService;
        this.orderRepository = orderRepository;
        this.orderDTOMapper = orderDTOMapper;
        this.userEntityService = userEntityService;
        this.productService = productService;
        this.cartRepository = cartRepository;
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
    public ResponseEntity<Object> placeOrder(@NotNull UserDetails userDetails, @NotNull OrderDTO orderDTO) {
        List<OrderDTO> orderDTOS = orderRepository.findByUser(userDetails.getUsername()).stream().map(orderDTOMapper).toList();
        Cart cart = cartRepository.findById(orderDTO.cartId()).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with id %d doesn't exist",orderDTO.cartId())));
        if (!orderDTOS.contains(orderDTO)){
            Order order = new Order(
                    orderDTO.price(),
                    productService.getProductById(orderDTO.product().id()),
                    optionService.getOrderOptions(orderDTO.options()),
                    cart,
                    userEntityService.getUserEntityByEmail(userDetails.getUsername())
            );
            orderRepository.save(order);
            List<Order> orders = cart.getOrders();
            orders.add(order);
            cart.setOrders(orders);
            cartRepository.save(cart);
            return ResponseHandler.generateResponse("Order saved successfully !",HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("Order already exists !!");
        }
    }


    @Override
    public Order getOrderById(final long orderId)
    {
        return orderRepository.fetchOrderById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Order with ID : %d could not be found in our system." , orderId))
        );
    }

    @Override
    public List<Order> fetchAllCartOrders(long cartId) {
        return orderRepository.fetchAllCartOrders(cartId);
    }

}