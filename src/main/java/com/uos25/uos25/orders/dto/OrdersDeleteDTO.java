package com.uos25.uos25.orders.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdersDeleteDTO {
    private Long orderId;
    private String reason;
}
