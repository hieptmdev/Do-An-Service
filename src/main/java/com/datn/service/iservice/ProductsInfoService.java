package com.datn.service.iservice;

import com.datn.dto.ProductInfoDTO;

import javax.servlet.http.HttpServletRequest;

public interface ProductsInfoService  extends  BaseService{
    ProductInfoDTO saveorupdatechitietProduct(HttpServletRequest request, ProductInfoDTO dto);
}
