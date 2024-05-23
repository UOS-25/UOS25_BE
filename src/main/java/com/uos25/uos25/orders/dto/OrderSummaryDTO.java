package com.uos25.uos25.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class OrderSummaryDTO{
    private String orderNumber;
    private LocalDate orderDate;
}