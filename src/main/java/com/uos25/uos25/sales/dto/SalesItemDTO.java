package com.uos25.uos25.sales.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesItemDTO {

    private String productCode; // Products 엔티티의 코드
    private int counts;
}
