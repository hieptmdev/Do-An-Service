package com.datn.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table
@Getter
@Setter
@Entity
public class ImageModel {
    public ImageModel() {
        super();
    }

    public ImageModel(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String thumbnail;
}
