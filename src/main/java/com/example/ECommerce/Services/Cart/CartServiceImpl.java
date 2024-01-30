package com.example.ECommerce.Services.Cart;

import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DAOs.Order.Order;
import com.example.ECommerce.DTOs.Cart.CartDTO;
import com.example.ECommerce.DTOs.Cart.CartDTOMapper;
import com.example.ECommerce.DTOs.Order.OrderDTO;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Exceptions.UnauthorizedActionException;
import com.example.ECommerce.Repositories.CartRepository;
import com.example.ECommerce.Services.Order.OrderService;
import com.example.ECommerce.Services.User.UserEntityService;
import com.example.ECommerce.Utility.ResponseHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    //Make sure the cart is not yet submitted
    @Override
    public ResponseEntity<Object> updateCart(UserDetails userDetails, CartDTO cartDTO) {
        Cart cart = cartRepository.findByIdAndUser(cartDTO.id(), userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with id %d doesn't exist",cartDTO.id())));
        List<Order> orders = new ArrayList<>();
        float sum =0;
        for (OrderDTO orderDTO : cartDTO.orders()){
            Order o =orderService.getOrderById(orderDTO.id());
            orders.add(o);
            sum =+ o.getPrice();
        }
        if (!cart.isSubmitted()){
            cart.setOrders(orders);
            cart.setTotalPrice(sum);
            cartRepository.save(cart);
            return ResponseHandler.generateResponse(cartDTOMapper.apply(cart), HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("Cart was already submitted, it can no longer be modified");
        }
    }

    @Override
    public ResponseEntity<Object> getCartById(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with id %d was not found !!", cartId)));
        return ResponseHandler.generateResponse(cartDTOMapper.apply(cart),HttpStatus.OK);
    }

    //Can be accessed by the carts owner and the Admin ..
    @Override
    public ResponseEntity<Object> fetchAllUserCarts(UserDetails userDetails, long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber -1, 8);
        List<Cart> carts = cartRepository.fetchAllUserCarts(userDetails.getUsername(),pageable);
        if (carts.isEmpty() && pageNumber >1){
            return fetchAllUserCarts(userDetails,1);
        }
        return ResponseHandler.generateResponse(carts,HttpStatus.OK,carts.size(),cartRepository.getUserCartsCount(userDetails.getUsername()));
    }

    @Override
    public ResponseEntity<Object> fetchAllCarts(long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber -1, 8);
        List<Cart> carts = cartRepository.fetchAllCartsPaged(pageable);
        if (carts.isEmpty() && pageNumber >1){
            return fetchAllCarts(1);
        }
        return ResponseHandler.generateResponse(carts,HttpStatus.OK,carts.size(),cartRepository.getAllCartsCount());
    }

    @Override
    public ResponseEntity<Object> fetchAllSubmittedCarts(long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber, 8);
        List<Cart> carts = cartRepository.fetchAllSubmittedCarts(pageable);
        return ResponseHandler.generateResponse(carts,HttpStatus.OK,carts.size(), cartRepository.getAllCartsCount());
    }

    //Get back to it with EmailSenderService
    @Override
    public ResponseEntity<Object> submitCart(UserDetails userDetails, long cartId) {
        Cart cart = cartRepository.findByIdAndUser(cartId,userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with Id %d doesn't exist",cartId)));
        cart.setSubmitted(true);
        cartRepository.save(cart);
        return ResponseHandler.generateResponse("Submission saved successfully", HttpStatus.OK);
    }

    //Get back to it with EmailSenderService
    @Override
    public ResponseEntity<Object> confirmCart(long cartId) {
        Cart cart = cartRepository.findByIdAndSubmission(cartId).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with Id %d doesn't exist",cartId)));
        cart.setConfirmed(true);
        cartRepository.save(cart);
        return ResponseHandler.generateResponse("Confirmation saved successfully",HttpStatus.OK);
    }

    //Get back to it with EmailSenderService
    @Override
    public ResponseEntity<Object> discardCart(UserDetails userDetails, long cartId) {
        Cart cart = cartRepository.findByIdAndUser(cartId, userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException(String.format("Cart with Id %d doesn't exist !!", cartId)));
        if (!cart.isConfirmed()){
            cartRepository.delete(cart);
            return ResponseHandler.generateResponse("Cart discarded successfully !!",HttpStatus.OK);
        } else {
            throw new UnauthorizedActionException("Cart was already confirmed, it can no longer be discarded !!");
        }
    }
}
