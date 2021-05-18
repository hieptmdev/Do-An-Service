package com.datn.repository;

import com.datn.dto.BrandDTO;
import com.datn.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select b from Brand b where lower(b.name) like concat('%', :name, '%')")
    List<Brand> search(String name);
}
