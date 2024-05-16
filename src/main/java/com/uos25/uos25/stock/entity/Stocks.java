package com.uos25.uos25.stock.entity;


import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stocks {

    @EmbeddedId
    private StoreProductId id;

    @ManyToOne
    @MapsId("storeId")
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "product_code")
    private Products products;

    @Column
    private int counts;

    public Stocks(Store store, Products products, int counts) {
        this.id = new StoreProductId(store.getId(), products.getProductCode());
        this.store = store;
        this.products = products;
        this.counts = counts;
    }
}
