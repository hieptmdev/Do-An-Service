package com.datn.service;

import com.datn.dto.ProductDto;
import com.datn.entity.Brand;
import com.datn.entity.Product;
import com.datn.entity.ProductType;
import com.datn.repository.BrandRepository;
import com.datn.repository.ProductRepository;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList;
        List<Product> productList = productRepository.findAll();
        if (!AppUtil.isNullOrEmpty(productList)){
            productDtoList = productList.stream()
                    .map(ent -> AppUtil.mapperEntAndDto(ent, ProductDto.class))
                    .collect(Collectors.toList());
            return productDtoList;
        }
        return null;
    }

    @Override
    public ProductDto saveOrUpdate(HttpServletRequest request, Object object) {
        ProductDto productDto = (ProductDto) object;
        Product product;
        if (productDto != null) {
            ProductType productType = AppUtil.NVL(productDto.getProductTypeId()) == 0L ? null :
                    productTypeRepository.findById(productDto.getProductTypeId()).orElse(null);
            Brand brand = AppUtil.NVL(productDto.getBrandId()) == 0L ? null :
                    brandRepository.findById(productDto.getBrandId()).orElse(null);
            if (AppUtil.NVL(productDto.getId()) == 0L) {
                product = AppUtil.mapperEntAndDto(productDto, Product.class);
                product.setCreatedDate(new Date());
                product.setUpdatedDate(new Date());
                product.setProductType(productType);
                product.setBrand(brand);
            }else {
                product = productRepository.findById(productDto.getId()).orElse(null);
                if (product != null){
                    Product data = AppUtil.mapperEntAndDto(productDto, Product.class);
                    data.setId(product.getId());
                    data.setUpdatedDate(new Date());
                    data.setProductType(productType);
                    data.setBrand(brand);
                    product = data;
                }
            }
            return AppUtil.mapperEntAndDto(productRepository.save(product), ProductDto.class);
        }
        return null;
    }

    @Override
    public ProductDto findById(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null){
            return AppUtil.mapperEntAndDto(product, ProductDto.class);
        }
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public ProductDto search(HttpServletRequest request, ProductDto dto) {
        return null;
    }
}
