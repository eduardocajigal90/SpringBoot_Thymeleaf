package com.quiox.test.projection;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class SectionRequestDto {

    private Double sizeInSquareMeters;

    List<ProductRequestDto> products = new ArrayList<>();

}
