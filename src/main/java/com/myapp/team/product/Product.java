package com.myapp.team.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class Product {

    private int productNo;
    private String productCondition;
    private String productName;
    private String productContent;
    private double productPrice;
    private MultipartFile imageFile;
}


