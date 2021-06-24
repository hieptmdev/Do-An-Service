package com.datn.controller;

import com.datn.entity.ImageModel;
import com.datn.service.iservice.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "image")
public class ImageUploadController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file,String name){
        try {
            File newFile = new File("F:\\DoAn_SpringBoots\\do-an-web\\src\\assets\\style\\img\\"+file.getOriginalFilename());
            FileOutputStream fileOutputStream;
            fileOutputStream=new FileOutputStream(newFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        ImageModel img = new ImageModel("assets/style/img/"+file.getOriginalFilename(), file.getOriginalFilename());
        imageService.save(img);
        return "upload thành công";
    }
    @GetMapping(path = { "/get/{imageName}" })
    public List<ImageModel> getImage(@PathVariable("imageName") String imageName) {
       List<ImageModel> img = imageService.findByName(imageName);
       return img;
    }
}
