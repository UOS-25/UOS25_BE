package com.uos25.uos25.sales.entity;

import com.uos25.uos25.products.entity.Products;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales; // 연관된 판매

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", nullable = false)
    private Products product; // 상품 코드 (Products 엔티티와 연관)

    @Column(nullable = false)
    private int counts; // 수량


    @Builder
    public SalesItem(Sales sales, Products product, int counts) {
        this.sales = sales;
        this.product = product;
        this.counts = counts;
    }
}
