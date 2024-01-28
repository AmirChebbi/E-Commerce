package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
