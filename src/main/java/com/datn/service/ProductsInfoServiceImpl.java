package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.BrandDTO;
import com.datn.dto.ProductDto;
import com.datn.dto.ProductInfoDTO;
import com.datn.entity.Brand;
import com.datn.entity.ProductInfo;
import com.datn.repository.ProductInfoRepository;
import com.datn.service.iservice.ProductService;
import com.datn.service.iservice.ProductsInfoService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProductsInfoServiceImpl implements ProductsInfoService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Override
    public <T extends BaseDto> List<T> findAll() {
        return null;
    }

    @Override
    public <T extends BaseDto> T saveOrUpdate(HttpServletRequest request, Object object) {
        return null;
    }

    @Override
    public ProductInfoDTO findById(HttpServletRequest request, Long id) {
        ProductInfo productInfo = productInfoRepository.findById(id).orElse(null);
        if(productInfo != null){
            return AppUtil.mapperEntAndDto(productInfo, ProductInfoDTO.class);
        }
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        return null;
    }

    @Override
    public ProductInfoDTO saveorupdatechitietProduct(HttpServletRequest request, ProductInfoDTO dto) {
        return null;
    }
}
