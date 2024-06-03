package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private int cartNo;
    private int userNo;
    private int productNo;
    private int optionNo;

    private int cartCount;

    private Product product; // 상품 정보 포함
    private Option option; // 옵션 정보 포함

    public void setUserNo(int userNo) {
    }
}
