package com.uos25.uos25.etc.parcel.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fromAddress;
    @Column
    private String toAddress;

    @Column
    private String fromPhoneNumber;

    @Column
    private String toPhoneNumber;

    @Column
    private int weight;

    @Column
    private String goods;

    @Column
    @Enumerated(EnumType.STRING)
    private ParcelState parcelState;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Parcel(String fromAddress, String toAddress, String fromPhoneNumber, String toPhoneNumber, int weight,
                  String goods, Store store) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.fromPhoneNumber = fromPhoneNumber;
        this.toPhoneNumber = toPhoneNumber;
        this.weight = weight;
        this.goods = goods;
        this.store = store;

        this.parcelState = ParcelState.PREPARE;
    }

    public void updateState(ParcelState state) {
        this.parcelState = state;
    }
}
