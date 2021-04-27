package com.datn.repository;

import com.datn.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
