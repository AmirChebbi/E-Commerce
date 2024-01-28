package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DAOs.Product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface OptionRepository extends JpaRepository<Option, Long> {



    @Transactional
    @Modifying
    @Query(value = "delete from Option o where o in :options")
    public void deleteAllOptions(@Param("options") final List<Option> options);

    @Transactional
    @Query(value = "select o from Option o where o.title=:title and o.product.id=:productId")
    public Optional<Option> findOptionByTitleAndProduct(@Param("title") String title, @Param("productId") long productId);

    @Transactional
    @Query(value = "select o from Option o where o.product.id =: productId")
    public List<Option> findAllProductOptions(@Param("productId") long productId);

    @Transactional
    @Query(value = "select o from Option o")
    List<Option> findAllPageable(Pageable pageable);

    @Transactional
    @Query(value = "select count(o) from Option o")
    int getCount();
}
