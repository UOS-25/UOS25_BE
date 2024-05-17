package com.uos25.uos25.event.entity;

import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "products_code")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    private int discount;

    @Column
    private String cinema;

    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

    @Builder
    public Event(EventType type, Products products, int discount, Store store, String cinema, LocalDate startDate,
                 LocalDate endDate) {
        this.type = type;
        this.products = products;
        this.discount = discount;
        this.cinema = cinema;
        this.store = store;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
