package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.OderDetailDTO;
import com.datn.repository.OrderDetailRepository;
import com.datn.service.iservice.OderdetaillService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OderDetailServiceImpl implements OderdetaillService {



    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Override
    public List<OderDetailDTO> findAll() {
        return orderDetailRepository.findAll()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, OderDetailDTO.class))
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
