package com.datn.repository;

import com.datn.dto.ChartData;
import com.datn.entity.Order;
import com.datn.entity.OrderDetail;
import com.datn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id);
    // tìm kiếm oder theo mã
    @Query("select o from Order o where lower(o.code) like concat('%', :code, '%')")
    List<Order> search(String code);

//    @Query("select o.createdDate from Order o")
//    ChartData getChartDate(In year);
}
