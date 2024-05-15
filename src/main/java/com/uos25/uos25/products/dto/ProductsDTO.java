package com.uos25.uos25.products.dto;

import com.uos25.uos25.products.entity.Products;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductsDTO {

    private String productName;
    private String productCode;
    private int salePrice;
    private int orderPrice;

    public static ProductsDTO toProductsDTO(Products products){
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductName(products.getProductName());
        productsDTO.setProductCode(products.getProductCode());
        productsDTO.setSalePrice(products.getSalePrice());
        productsDTO.setOrderPrice(products.getOrderPrice());
        return productsDTO;
    }
}
