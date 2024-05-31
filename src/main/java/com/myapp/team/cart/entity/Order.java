package com.myapp.team.cart.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    private Long ordersId;
    private String productNo;
    private String orderDate; // Date 타입 대신 String으로 사용, 나중에 변환 필요
    private String orderUserName;
    private String orderUserPhone;
    private String orderUserAdress;
    private String orderMessage;
}
