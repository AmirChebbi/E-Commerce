package com.example.ECommerce.Services.Cart;

import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DAOs.User.UserEntity;
import com.example.ECommerce.DTOs.Cart.CartDTO;
import com.example.ECommerce.DTOs.Cart.CartDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.CartRepository;
import com.example.ECommerce.Services.Order.OrderService;
import com.example.ECommerce.Services.User.UserEntityService;
import com.example.ECommerce.Utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartServiceImpl implements CartService{
    private final UserEntityService userEntityService;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final CartDTOMapper cartDTOMapper;
    public CartServiceImpl(UserEntityService userEntityService, CartRepository cartRepository, OrderService orderService, CartDTOMapper cartDTOMapper) {
        this.userEntityService = userEntityService;
        this.cartRepository = cartRepository;
        this.orderService = orderService;
        this.cartDTOMapper = cartDTOMapper;
    }

    @Override
    public ResponseEntity<Object> createCart(UserDetails userDetails, CartDTO cartDTO) {
        Cart cart = new Cart(
                userEntityService.getUserEntityByEmail(userDetails.getUsername()),
                new ArrayList<Order>()
        );
        return ResponseHandler.generateResponse(cartDTOMapper.apply(cartRepository.save(cart)), HttpStatus.OK);
    }

    //Mistake here, watch it !!!!!!
    @Override
    public ResponseEntity<Object> updateCart(UserDetails userDetails, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(cartDTO.id()).orElseThrow(()-> new ResourceNotFoundException("Cart doesn't exist !!"));
        List<Order> orders = orderService.fetchAllCartOrders(cartDTO.id());
        if (Objects.equals(cart.getUserEntity(), (UserEntity)userDetails)){
            cart.setOrders();
        }
    }

    @Override
    public ResponseEntity<Object> getCartById(UserDetails userDetails, long cartId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> fetchAllUserCarts(UserDetails userDetails, long pageNumber) {
        return null;
    }

    @Override
    public ResponseEntity<Object> fetchAllCarts(long pageNumber) {
        return null;
    }

    @Override
    public ResponseEntity<Object> submitCart(UserDetails userDetails) {
        return null;
    }

    @Override
    public ResponseEntity<Object> confirmCart() {
        return null;
    }

    @Override
    public ResponseEntity<Object> discardCart(UserDetails userDetails, long cartId) {
        return null;
    }


}
