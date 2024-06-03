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

    private String productName;
    private int productPrice;
    private String options;

    // 외부 테이블들
    public void setUserNo(int userNo) {
    }
    public void setUserId(String userId){
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    public String getOptions() {
        return options;
    }
    public void setOptions(String options) {
        this.options = options;
    }
}
