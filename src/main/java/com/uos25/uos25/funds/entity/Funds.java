package com.uos25.uos25.funds.entity;

import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Funds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funds_id")
    private Long id;

    @Column
    private int totalFunds = 50000;

    @Column
    private int headPayment; //본사 납입급

    @Column
    private int maintenanceExpense; //유지비

    @Column
    private int personalExpense; // 인건비

    @Column
    private int sales; //매출

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Funds(int headPayment, int maintenanceExpense, int personalExpense, int sales, Store store) {
        this.headPayment = headPayment;
        this.maintenanceExpense = maintenanceExpense;
        this.personalExpense = personalExpense;
        this.sales = sales;
        this.store = store;

        updateTotalFunds();
    }

    public void updateTotalFunds() {
        this.totalFunds += sales;
        this.totalFunds -= (headPayment + maintenanceExpense + personalExpense);
    }

    public void withdrawal(int money) {
        totalFunds -= money;

        updateTotalFunds();
    }

    public void decideMaintenanceExpense(int maintenanceExpense) {
        this.maintenanceExpense = maintenanceExpense;

        updateTotalFunds();
    }
}
