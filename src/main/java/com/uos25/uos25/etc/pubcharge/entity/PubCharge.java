package com.uos25.uos25.etc.pubcharge.entity;

import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
public class PubCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_charge_id")
    private Long id;

    @Column
    private int money;

    @Column
    private String accountNumber; //계좌번호

    @Column
    @Enumerated(EnumType.STRING)
    private PubChargeType pubChargeType;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    
    @Builder
    public PubCharge(int money, String accountNumber, PubChargeType pubChargeType, Store store) {
        this.money = money;
        this.accountNumber = accountNumber;
        this.pubChargeType = pubChargeType;
        this.store = store;
    }
}
