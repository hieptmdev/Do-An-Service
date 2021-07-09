package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.BrandDTO;
import com.datn.dto.ProductDto;
import com.datn.dto.ProductInfoDTO;
import com.datn.entity.*;
import com.datn.repository.ColorReponsitory;
import com.datn.repository.ProductInfoRepository;
import com.datn.repository.ProductRepository;
import com.datn.service.iservice.ProductService;
import com.datn.service.iservice.ProductsInfoService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ProductsInfoServiceImpl implements ProductsInfoService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ColorReponsitory colorReponsitory;
    @Override
    public <T extends BaseDto> List<T> findAll() {
        return null;
    }

    //thêm chi tiết cho 1 sản phẩm theo id
    @Override
    public ProductInfoDTO saveOrUpdate(HttpServletRequest request, Object object) {
        ProductInfoDTO productInfoDTO = (ProductInfoDTO) object;
        ProductInfo productInfo ;
        // kiểm tra đầu vào fontend
        if(productInfoDTO !=null){
            // kiểm tra id của productInffo = null => thêm mới info
            if(AppUtil.NVL(productInfoDTO.getId())==0L){
                //chuyển từ DTO => Entity để lưu vào database
                productInfo = AppUtil.mapperEntAndDto(productInfoDTO, ProductInfo.class);
                productInfo.setCreatedDate(new Date());
                productInfo.setUpdatedDate(new Date());
                //láy id của product => lưu theo id
                productInfo.setProduct(productRepository.findById(productInfoDTO.getProductId()).orElse(null));
                productInfo.setColor(colorReponsitory.findById(productInfoDTO.getColorId()).orElse(null));
            }
            else {

                productInfo = productInfoRepository.findById(productInfoDTO.getId()).orElse(null);
                if (productInfo != null){
                    ProductInfo dataProInfo = AppUtil.mapperEntAndDto(productInfoDTO,ProductInfo.class); // dataBrand sau khi map đã có dủ hết data r
                    dataProInfo.setId(productInfo.getId());
                    dataProInfo.setUpdatedDate(new Date());
                    dataProInfo.setProduct(productRepository.findById(productInfoDTO.getProductId()).orElse(null));
                    dataProInfo.setColor(colorReponsitory.findById(productInfoDTO.getColorId()).orElse(null));
                    productInfo = dataProInfo;
                }

            }
            return AppUtil.mapperEntAndDto(productInfoRepository.save(productInfo), ProductInfoDTO.class);
        }
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
    @Transactional
    public Boolean delete(HttpServletRequest request, Long id) {
        ProductInfo productInfo = productInfoRepository.findById(id).orElse(null);
        if(productInfo != null){
            productInfoRepository.delete(productInfo);
            return  true;
        }
        return false;
    }

    @Override
    public ProductInfoDTO saveorupdatechitietProduct(HttpServletRequest request, ProductInfoDTO dto) {
        return null;
    }
}
