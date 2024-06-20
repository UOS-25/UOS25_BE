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
    @Column(name = "parcel_id")
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
    private String fromName;

    @Column
    private String toName;

    @Column
    private int weight;

    @Column
    private String item;

    @Column
    @Enumerated(EnumType.STRING)
    private ParcelState parcelState;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Parcel(String fromAddress, String toAddress, String fromPhoneNumber, String toPhoneNumber, String fromName,
                  String toName, int weight, String item, ParcelState parcelState, Store store) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.fromPhoneNumber = fromPhoneNumber;
        this.toPhoneNumber = toPhoneNumber;
        this.fromName = fromName;
        this.toName = toName;
        this.weight = weight;
        this.item = item;
        this.parcelState = parcelState;
        this.store = store;
    }

    public void updateState(ParcelState state) {
        this.parcelState = state;
    }
}
