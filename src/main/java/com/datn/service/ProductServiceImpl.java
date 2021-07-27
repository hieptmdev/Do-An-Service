package com.datn.service;

import com.datn.dto.*;
import com.datn.entity.*;
import com.datn.repository.BrandRepository;
import com.datn.repository.ColorReponsitory;
import com.datn.repository.ProductRepository;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    @Autowired
    ColorReponsitory colorReponsitory;
    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList;
        List<Product> productList = productRepository.findAll();
        if (!AppUtil.isNullOrEmpty(productList)){
            productDtoList = productList.stream()
                    //chuyển từ ent => dto
                    .map(ent -> {
                        ProductDto dto = AppUtil.mapperEntAndDto(ent, ProductDto.class);
                        dto.setColoList(ent.getProductInfoList()
                                .stream()
                                .map(productInfo -> {
                                    ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                    colorDTO.setProductInfoId(productInfo.getId());
                                    return colorDTO;
                                })
                                .collect(Collectors.toList())
                        );
                        return dto;
                    })
                    .collect(Collectors.toList());
            return productDtoList; // trả kết quả
        }
        return null;
    }
    //sản phẩm new
    @Override
    public List<ProductDto> findNew() {
        return productRepository.getProductNew()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }
    //sản phẩm sale
    @Override
    public List<ProductDto> findSale() {
        return productRepository.getProductSale()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }


    //Lưu và update SP
    @Override
    public ProductDto saveOrUpdate(HttpServletRequest request, Object object) {
        ProductDto productDto = (ProductDto) object;
        Product product = null;
        if (productDto != null) {
            ProductType productType = AppUtil.NVL(productDto.getProductTypeId()) == 0L ? null :
                    productTypeRepository.findById(productDto.getProductTypeId()).orElse(null);
            Brand brand = AppUtil.NVL(productDto.getBrandId()) == 0L ? null :
                    brandRepository.findById(productDto.getBrandId()).orElse(null);

            //Lưu mới product
            if (AppUtil.NVL(productDto.getId()) == 0L) {
                product = AppUtil.mapperEntAndDto(productDto, Product.class);
                product.setCreatedDate(new Date());
                product.setUpdatedDate(new Date());
                product.setProductType(productType);
                product.setBrand(brand);
                product.setImage("assets/style/img/"+productDto.getImage());
            }
            //update
            else {
                Product data = productRepository.findById(productDto.getId()).orElse(null);
                if (data != null){
                    product = AppUtil.mapperEntAndDto(productDto, Product.class);
                    product.setId(data.getId());
                    product.setUpdatedDate(new Date());
                    product.setProductType(productType);
                    product.setBrand(brand);
                    product.setImage("assets/style/img/"+productDto.getImage());
                }
            }
            return AppUtil.mapperEntAndDto(productRepository.save(product), ProductDto.class);
        }
        return null;
    }
    // tìm kiếm sp theo Id
    @Override
    public ProductDto findById(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null){
            ProductDto productDto = AppUtil.mapperEntAndDto(product, ProductDto.class);
            productDto.setColoList(product.getProductInfoList()
                    .stream()
                    .map(productInfo -> {
                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                        colorDTO.setProductInfoId(productInfo.getId());
                        return colorDTO;
                    })
                    .collect(Collectors.toList())
            );
            return productDto;
        }
        return null;
    }
    //tìm kiếm sản phẩm theo hãng
    @Override
    public List<ProductDto> findAllBrand(HttpServletRequest request, Long id) {
        return productRepository.getAllByBrands(id)
                .stream()
                .map(obj ->{
                    ProductDto dto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    dto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
                                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                        colorDTO.setProductInfoId(productInfo.getId());
                                        return colorDTO;
                                    })
                            .collect(Collectors.toList())
                    );
                    return dto;
                }).
                        collect(Collectors.toList());
    }

    //lọc sản phẩm theo hãng và thể loại
    @Override
    public List<ProductDto> findFillter(HttpServletRequest request, ProductDto dto) {
        return productRepository.getProductByBrandAndProductType(dto.getBrandId(), dto.getProductTypeId())
                .stream()
                .map(obj ->{
                    ProductDto productDto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    productDto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
                                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                        colorDTO.setProductInfoId(productInfo.getId());
                                        return colorDTO;
                                    })
                            .collect(Collectors.toList())
                    );
                    return productDto;
                }).
                        collect(Collectors.toList());
    }


    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return true;
            //xoa
        }
        return false;
    }

    //tìm kiếm sp theo người dùng nhập
    @Override
    public List<ProductDto> search(HttpServletRequest request, ProductDto dto) {
        return productRepository.search(dto.getName().toLowerCase())
                .stream()
                .map(obj ->{
                    ProductDto productDto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    productDto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
                                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                        colorDTO.setProductInfoId(productInfo.getId());
                                        return colorDTO;
                                    })
                            .collect(Collectors.toList())
                    );
                    return productDto;
                }).
                        collect(Collectors.toList());
    }

    //Chi tiết product
    @Override
    public ProductDto searchDetailProduct(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product !=null){
            return AppUtil.mapperEntAndDto(product, ProductDto.class);
        }
        return null;

    }

    @Override
    public List<ColorDTO> findAllColor(HttpServletRequest request) {
        return colorReponsitory.findAll().stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ColorDTO.class))
                .collect(Collectors.toList());
    }
}
