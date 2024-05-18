package com.uos25.uos25.loss.dto;

import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LossDTO {

    private String productsCode;
    private int counts;

    private String reason;
}
