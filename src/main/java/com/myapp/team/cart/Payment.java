package com.myapp.team.cart;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long paymentNo;
    private Long ordersId;
    private String paymentMethod;
    private Long paymentAmount;
    private String paymentDate;
}
