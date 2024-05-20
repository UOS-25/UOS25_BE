package com.uos25.uos25.etc.lotto.entity;


import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Lotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lotto_id")
    private Long id;

    @Column
    private int price;

    @Column
    private String winningNumbers;

    @Column
    private int bonusNumbers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Lotto(int price, String winningNumbers, Store store) {
        this.price = price;
        this.winningNumbers = winningNumbers;
        this.store = store;
    }

    public void update(int price, String winningNumbers) {
        this.price = price;
        this.winningNumbers = winningNumbers;
    }
}
