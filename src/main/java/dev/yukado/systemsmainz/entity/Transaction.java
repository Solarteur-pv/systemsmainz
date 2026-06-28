package dev.yukado.systemsmainz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // optional reference to User

    @Column(nullable = false)
    private String type; // purchase, payout, fee, refund

    @Column(nullable = false)
    private Double amount; // + income, - expense

    @Column
    private Double price; // ticket price (only for purchase)

    @Column(length = 50)
    private String country; // ISO code

    @Column(name = "cityname", length = 191)
    private String cityname; // optional, for better reporting

    @Column(length = 10)
    private String currency; // EUR, USD...

    @Column(name = "payment_id")
    private Long paymentId; // optional reference

    @Column(name = "processed_tx_id")
    private Long processedTxId; // optional reference

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    // 🔥 WICHTIG: Hibernate braucht diesen Konstruktor
    public Transaction() {
    }

    // Optional: eigener Konstruktor
    public Transaction(Long userId, String type, Double amount, Double price, String country, String cityname, String currency, Long paymentId, Long processedTxId) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.country = country;
        this.cityname = cityname;
        this.currency = currency;
        this.paymentId = paymentId;
        this.processedTxId = processedTxId;
    }

    // getters + setters

}

