package com.myapp.team.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private int categoryNo;
    private String categoryName; // 의류, 가구 등 ..
    private String categorySection; // 상의, 하의, 신발, 의자, 책상 등 ..
}
