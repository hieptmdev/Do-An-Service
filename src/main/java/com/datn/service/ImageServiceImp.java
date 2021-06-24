package com.datn.service;

import com.datn.dto.ProductDto;
import com.datn.entity.ImageModel;
import com.datn.entity.Product;
import com.datn.repository.ImageRepository;
import com.datn.repository.ProductRepository;
import com.datn.service.iservice.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void save(ImageModel imageModel) {

        imageRepository.save(imageModel);
    }
    @Override
    public List<ImageModel> findByName(String name) {
        return imageRepository.findByName(name);
    }
}
