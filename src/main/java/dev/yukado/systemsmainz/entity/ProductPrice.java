package dev.yukado.systemsmainz.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_prices")
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ein Produkt hat genau einen Preis (für B2C)
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Brutto-Verkaufspreis für deinen Shop
    @Column(nullable = false)
    private BigDecimal priceGross;

    // Netto-Preis (optional, aber nützlich für Berechnungen)
    private BigDecimal priceNet;

    // Mehrwertsteuer (19% oder 7%)
    private BigDecimal vatRate;

    // Für Sync mit UNI Elektro & Amazon
    private LocalDateTime lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(BigDecimal priceGross) {
        this.priceGross = priceGross;
    }

    public BigDecimal getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(BigDecimal priceNet) {
        this.priceNet = priceNet;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

