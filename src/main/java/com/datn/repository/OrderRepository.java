package com.datn.repository;

import com.datn.dto.OderDTO;
import com.datn.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findAllByUserId(Long id);
    // tìm kiếm oder theo mã
    @Query("select o from Order o where lower(o.code) like concat('%', :code, '%')")
    List<Order> search(String code);
}
