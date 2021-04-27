package com.datn.repository;

import com.datn.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
