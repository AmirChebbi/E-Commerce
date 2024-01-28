package com.example.ECommerce.Services.Cart;

import com.example.ECommerce.DAOs.Cart.Cart;
import com.example.ECommerce.DTOs.Cart.CartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartService {

    public ResponseEntity<Object> createCart(UserDetails userDetails, CartDTO cartDTO);
    public ResponseEntity<Object> updateCart(UserDetails userDetails, CartDTO cartDTO);
    public ResponseEntity<Object> getCartById(UserDetails userDetails, long cartId);
    public ResponseEntity<Object> fetchAllUserCarts(UserDetails userDetails, long pageNumber);
    public ResponseEntity<Object> fetchAllCarts(long pageNumber);
    public ResponseEntity<Object> submitCart(UserDetails userDetails);
    public ResponseEntity<Object> confirmCart();
    public ResponseEntity<Object> discardCart(UserDetails userDetails, long cartId);
}
