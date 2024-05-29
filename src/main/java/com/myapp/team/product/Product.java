package com.myapp.team.product;

import com.myapp.team.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class Product {

    private int productNo;
    private String productCondition;
    private String categoryName;
    private String productName;
    private String productContent;
    private double productPrice;
//    private MultipartFile imageFile;
    private String imageFileName;  // 제품 이름

    private List<Option> options;

    // 옵션 테이블 추가해야함.
}


