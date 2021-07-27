package com.datn.service;

import com.datn.ExcelHelper.ExcelHelper;
import com.datn.dto.OderDTO;
import com.datn.dto.UserDto;
import com.datn.entity.Order;
import com.datn.entity.User;
import com.datn.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelService {
    @Autowired
    OrderRepository orderRepository;

    public byte[] dowloadExcel(OderDTO dto) {
        List<Order> oderOrderList = orderRepository.dowloadOrder(dto);
        return ExcelHelper.tutorialsToExcel(oderOrderList);
    }
}
