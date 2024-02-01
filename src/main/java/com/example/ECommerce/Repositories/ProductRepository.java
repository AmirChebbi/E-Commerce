package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "select p from Product p where p.id = :productId")
    Optional<Product> fetchProductById(@Param("productId") final long productId);

    @Query(value = "select p from Product p")
    List<Product> fetchAllProducts(Pageable pageable);

    @Query(value = "select count(p) from Product p")
    long getTotalProductCount();

    @Modifying
    @Transactional
    @Query(value = "delete from Product p where p.id = :productId")
    void deleteProductById(@Param("productId") final long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p IN :products")
    void deleteAllProducts(@Param("products") List<Product> products);


    @Query(value = "select p from Product p where p.title=:title and p.reference=:reference")
    Optional<Product> findByTitleAndReference(@Param("title")String title, @Param("reference") String reference);
}
