package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import com.myapp.team.user.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int cartNo;
    private String UserId;
    private int productNo;
    private int optionNo;
    private int productCount;
    private int userNo;
    private Product product;
    private Option option;
    private User user;
}
