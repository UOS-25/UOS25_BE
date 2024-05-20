package com.uos25.uos25.loss.entity;

import com.uos25.uos25.loss.dto.LossDTO;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Loss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Products products;

    @Column
    private int counts;

    @Column
    private String reason;

    public void toLoss(LossDTO lossDTO, Store store, Products products){
        this.setStore(store);
        this.setProducts(products);
        this.setCounts(lossDTO.getCounts());
        this.setReason(lossDTO.getReason());
    }

}
