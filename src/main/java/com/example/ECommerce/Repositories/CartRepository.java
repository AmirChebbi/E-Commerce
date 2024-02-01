package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Cart.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT c from Cart c where c.userEntity.email=:email")
    List<Cart> fetchAllUserCarts(@Param("email") String email, Pageable pageable);
    @Query(value = "select count(c) from Cart c where c.userEntity.email=:email")
    long getUserCartsCount(@Param("email") String email);
    @Query(value = "select c from Cart c")
    List<Cart> fetchAllCartsPaged(Pageable pageable);
    @Query(value = "select count(c) from Cart c")
    long getAllCartsCount();

    @Query(value = "select c from Cart c where c.id=:cartId  and c.userEntity.email=:email ")
    Optional<Cart> findByIdAndUser(@Param("cartId") long cartId, @Param("email") String email);
    @Query(value = "select c from Cart c where c.isSubmitted=:isSubmitted")
    List<Cart> fetchAllSubmittedCarts(Pageable pageable, @Param("isSubmitted") boolean isSubmitted);
    @Query(value = "select count(c) from Cart c where c.isSubmitted=:isSubmitted")
    long getSubmittedCartCount(@Param("isSubmitted") boolean isSubmitted);
    @Query(value = "select c from Cart c where c.id=:cartId and c.isSubmitted=:isSubmitted")
    Optional<Cart> findByIdAndSubmission(@Param("cartId") long cartId, @Param("isSubmitted") boolean isSubmitted);
}
