package com.example.ECommerce.Repositories;


import com.example.ECommerce.DAOs.File.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface FileDataRepository extends JpaRepository<FileData,Integer> {


    @Query(value = "select fd from FileData fd where fd.id = :id")
    Optional<FileData> fetchFileDataById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "delete from FileData f where f.id = :id")
    void deleteFileDataById(@Param("id") final long id);

    @Transactional
    @Modifying
    @Query(value = "delete from FileData f where f in :files")
    void deleteAllFiles(@Param("files") List<FileData> files);
}
