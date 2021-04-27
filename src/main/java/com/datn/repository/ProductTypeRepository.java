package com.datn.repository;

import com.datn.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
