package com.datn.service.iservice;


import com.datn.dto.ProductDto;
import com.datn.entity.ImageModel;
import com.datn.entity.Product;

import java.util.List;

public interface ImageService {
    public void saveProduct(Product product);
    public void save(ImageModel imageModel);
    List<ImageModel> findByName(String name);
}
