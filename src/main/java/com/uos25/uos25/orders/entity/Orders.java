package com.uos25.uos25.orders.entity;

import com.uos25.uos25.orders.dto.OrdersSaveDTO;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(name = "FK_STORE_ID"))
    private Store store;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false, referencedColumnName = "productCode", foreignKey = @ForeignKey(name = "FK_PRODUCT_CODE"))
    private Products product;

    @Column(nullable = false)
    private int counts;

    @Column(nullable = false)
    private boolean confirm;

    public static Orders toSaveOrders(OrdersSaveDTO ordersSaveDTO, Store store, Products product, int counts) {
        Orders orders = new Orders();
        orders.setOrderNumber(ordersSaveDTO.getOrderNumber());
        orders.setStore(store);
        orders.setOrderDate(LocalDate.now());
        orders.setProduct(product);
        orders.setCounts(counts);
        orders.setConfirm(false);
        return orders;
    }
}
