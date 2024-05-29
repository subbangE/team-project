package com.myapp.team.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductDTO {

    private int productNo;
    private String productName;
    private double productPrice;
    private String productContent;
    private MultipartFile imageFile;
}
