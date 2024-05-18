package com.uos25.uos25.sales.entity;

import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    private Long id; // 영수증 번호랑 판매 번호랑 동일한거로 사용할거임 ㅇㅇ(명세서엔 다르게 구분해야할 듯)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 지점번호 (Store 엔티티와 연관)

    @Column(nullable = false)
    private LocalDate salesDate; // 판매시간

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesItem> salesItems; // 판매 항목 리스트

    @Column(nullable = false)
    private Boolean isCancelled; // 구매취소여부

    @Column(nullable = false)
    private int totalAmount; // 판매 금액

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalesType type;

    @Column(nullable = false)
    private String gender; // 성별

    @Column(nullable = false)
    private String ageGroup; // 연령대

    @Builder
    public Sales(Store store, LocalDate salesDate, List<SalesItem> salesItems, Boolean isCancelled, int totalAmount, SalesType type, String gender, String ageGroup) {
        this.store = store;
        this.salesDate = salesDate;
        this.salesItems = salesItems;
        this.isCancelled = isCancelled;
        this.totalAmount = totalAmount;
        this.type = type;
        this.gender = gender;
        this.ageGroup = ageGroup;
    }

}
