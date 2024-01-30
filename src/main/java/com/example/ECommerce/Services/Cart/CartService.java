package com.example.ECommerce.Services.Cart;

import com.example.ECommerce.DTOs.Cart.CartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartService {

    public ResponseEntity<Object> createCart(UserDetails userDetails, CartDTO cartDTO);
    public ResponseEntity<Object> updateCart(UserDetails userDetails, CartDTO cartDTO);
    public ResponseEntity<Object> getCartById(long cartId);
    public ResponseEntity<Object> fetchAllUserCarts(UserDetails userDetails, long pageNumber);
    public ResponseEntity<Object> fetchAllCarts(long pageNumber);
    public ResponseEntity<Object> fetchAllSubmittedCarts(long pageNumber);
    public ResponseEntity<Object> submitCart(UserDetails userDetails, long cartId);
    public ResponseEntity<Object> confirmCart(long cartId);
    public ResponseEntity<Object> discardCart(UserDetails userDetails, long cartId);
}
