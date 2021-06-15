package com.datn.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorDTO extends  BaseDto{
    private String code;
    private String name;
    private Long productInfoId;

    public ColorDTO() {}
}
