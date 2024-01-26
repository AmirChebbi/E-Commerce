package com.example.ECommerce.Repositories;

import com.example.ECommerce.DAOs.Option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface OptionRepository extends JpaRepository<Option, Long> {



    @Transactional
    @Modifying
    @Query(value = "delete from Option o where o in :options")
    public void deleteAllOptions(@Param("options") final List<Option> options);
}
