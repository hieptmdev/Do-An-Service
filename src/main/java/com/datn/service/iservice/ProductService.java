package com.datn.service.iservice;

import com.datn.dto.ProductDto;

import javax.servlet.http.HttpServletRequest;

public interface ProductService extends BaseService{
    ProductDto search(HttpServletRequest request, ProductDto dto);
}
