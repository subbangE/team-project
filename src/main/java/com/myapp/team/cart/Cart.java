package com.myapp.team.cart;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private int cartNo;
    private int userNo;
    private String productNo;
    private int cartCount;

    public Cart(int cartNo, String productNo, int cartCount) {
        this.cartNo = cartNo;
        this.productNo = productNo;
        this.cartCount = cartCount;
    }
}
