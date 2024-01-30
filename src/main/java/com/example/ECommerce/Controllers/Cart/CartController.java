package com.example.ECommerce.Controllers.Cart;

import com.example.ECommerce.DTOs.Cart.CartDTO;
import com.example.ECommerce.Services.Cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartDTO cartDTO){
        return cartService.createCart(userDetails , cartDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<Object> updateCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartDTO cartDTO){
        return cartService.updateCart(userDetails,cartDTO);
    }
    @GetMapping("/getById/{cartId}")
    public ResponseEntity<Object> getCartById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long cartId){
        return cartService.getCartById(cartId);
    }
    @GetMapping("/getAllByUser")
    public ResponseEntity<Object> fetchAllUserCarts(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long pageNumber){
        return cartService.getCartById(pageNumber);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> fetchAllCarts(@RequestParam long pageNumber){
        return cartService.getCartById(pageNumber);
    }
    @GetMapping("/getAllSubmitted")
    public ResponseEntity<Object> fetchAllSubmittedCarts(@RequestParam long pageNumber){
        return cartService.getCartById(pageNumber);
    }
    @GetMapping("/submit")
    public ResponseEntity<Object> submitCart(UserDetails userDetails, long cartId){
        return cartService.submitCart(userDetails,cartId);
    }
    @PutMapping("/confirm/{id}")
    public ResponseEntity<Object> confirmCart(@PathVariable long cartId){
        return cartService.confirmCart(cartId);
    }
    @DeleteMapping("/discard/{cartId}")
    public ResponseEntity<Object> discardCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long cartId){
        return cartService.discardCart(userDetails,cartId);
    }
}
