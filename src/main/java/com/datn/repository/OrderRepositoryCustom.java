package com.datn.repository;

import com.datn.dto.OderDTO;
import com.datn.entity.Order;
import com.datn.entity.User;

import java.util.List;
import java.util.Map;

public interface OrderRepositoryCustom {
    Map<String, Double> getChartDateByYear(Long year);
    List<Order> dowloadOrder (OderDTO dto);
    List<Order> findByCode (Integer status);
}
