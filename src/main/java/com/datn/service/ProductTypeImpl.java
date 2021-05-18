package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.ProductDto;
import com.datn.dto.ProductTypeDTO;
import com.datn.entity.ProductType;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.service.iservice.ProductTypeService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;import java.util.stream.Collectors;


@Service
public class ProductTypeImpl implements ProductTypeService{

     @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductTypeDTO> findAll() {
        return productTypeRepository.getAllParent()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductTypeDTO.class))
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


    @Override
    public List<ProductTypeDTO> findAllCategory(HttpServletRequest request, Long id) {
        return productTypeRepository.getAllByCategory(id)
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductTypeDTO.class)).
                collect(Collectors.toList());
    }
}




