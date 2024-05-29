package com.myapp.team.product.option;

import com.myapp.team.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {

    private int optionNo;  // 옵션 번호 (한 상품에 여러 옵션 가능)
    private String optionName; // 옵션 이름 (예: 색상, 사이즈)
    private int optionCount;  // 옵션 상품 수량

    private Product product;  // 옵션 상품이 속한 상품
}
