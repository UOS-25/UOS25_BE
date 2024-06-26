package com.uos25.uos25.etc.atm.entity;

import com.uos25.uos25.common.entity.BaseTimeEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Atm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atm_id")
    private Long id;

    @Column
    private int money;

    @Column
    private String accountHolder;

    @Column
    private String accountNumber;

    @Column
    private String bank;

    @Column
    @Enumerated(EnumType.STRING)
    private WorkType workType;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Atm(int money, String accountHolder, String accountNumber, String bank, WorkType workType, Store store) {
        this.money = money;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.workType = workType;
        this.store = store;
    }
}
