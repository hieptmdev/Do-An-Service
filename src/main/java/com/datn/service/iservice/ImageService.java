package com.datn.service.iservice;


import com.datn.entity.ImageModel;

import java.util.List;

public interface ImageService {
    public void save(ImageModel imageModel);
    List<ImageModel> findByName(String name);
}
