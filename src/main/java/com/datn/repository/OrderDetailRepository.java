package com.datn.repository;

import com.datn.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    //láy ra oderdetal theo oderCode
    @Query("select od from OrderDetail od INNER JOIN Order o ON od.order.id = o.id WHERE o.code = :code")
    List<OrderDetail> findAllByCode(String code);
}
