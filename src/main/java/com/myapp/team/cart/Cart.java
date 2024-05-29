package com.myapp.team.cart;

import lombok.*;


/**
 * Cart 클래스는 장바구니 항목을 나타냅니다.
 * Lombok 라이브러리를 사용하여 보일러플레이트 코드를 줄입니다.
 */
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
