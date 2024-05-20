package com.uos25.uos25.products.entity;

import com.uos25.uos25.products.dto.ProductsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Products {

    @Column(nullable = false)
    private String productName;

    @Id
    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private int salePrice;

    @Column(nullable = false)
    private int orderPrice;

    public static Products toSaveProducts(ProductsDTO productsDTO){
        Products products = new Products();
        products.setProductName(productsDTO.getProductName());
        products.setProductCode(productsDTO.getProductCode());
        products.setSalePrice(productsDTO.getSalePrice());
        products.setOrderPrice(productsDTO.getOrderPrice());
        return products;
    }
}
