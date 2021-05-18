package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.OderDTO;
import com.datn.repository.OrderRepository;
import com.datn.service.iservice.OrderService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;
    @Override
    public  List<OderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, OderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public <T extends BaseDto> T saveOrUpdate(HttpServletRequest request, Object object) {
        return null;
    }

    @Override
    public <T extends BaseDto> T findById(HttpServletRequest request, Long id) {
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        return null;
    }
}
