package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class Color extends BaseEntity{
    private String code;
    private String name;

    public Color(){}
}
