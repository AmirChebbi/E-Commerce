package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "select o from Order o where o.id = :orderId")
    Optional<Order> fetchOrderById(@Param("orderId") final long orderId);
    @Query(value = "select o from Order o order by o.id")
    List<Order> fetchAllOrders(Pageable pageable);
    @Query(value = "select o from Order o where o.user.id = :userId order by  o.id")
    List<Order> fetchOrdersFromUser(@Param("userId") final UUID userId, Pageable pageable);

    @Query(value = "select  count(o) from Order o")
    long getTotalOrderCount();
    @Query(value = "select count(o) from Order  o where o.user.id = :userId")
    long getTotalOrderCountByUser(@Param("userId") final UUID userId);
    @Query(value = "select o from Order o where o.cart.id=:cartId")
    List<Order> fetchAllCartOrders(@Param("cartId")long cartId);

    @Query(value = "select o from Order o where o.user.email=:email")
    List<Order> findByUser(@Param("email") String email);
}
