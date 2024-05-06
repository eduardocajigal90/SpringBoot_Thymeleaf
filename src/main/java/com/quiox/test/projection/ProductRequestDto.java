package com.quiox.test.projection;

import com.quiox.test.model.PackagingType;
import com.quiox.test.model.ProductType;

import lombok.*;

@Getter
@Setter
public class ProductRequestDto {

    private String name;

    private double size;

    private String color;

    private double price;

    private boolean isFragile;

    private PackagingType packagingType;

    private String lot;

    private Long quantity;

    private ProductType productType;

    private int voltage;

    private String measurements;

}
