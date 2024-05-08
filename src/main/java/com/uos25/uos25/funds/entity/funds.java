package com.uos25.uos25.funds.entity;

import com.uos25.uos25.Store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class funds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funds_id")
    private Long id;

    @Column
    private int totalFunds;

    @Column
    private int headPayment; //본사 납입급

    @Column
    private int maintenanceExpense; //유지비

    @Column
    private int personalExpense; // 인건비

    @Column
    private int sales; //매출

    @OneToOne
    @JoinColumn(name = "shop_id")
    private Store store;

}
