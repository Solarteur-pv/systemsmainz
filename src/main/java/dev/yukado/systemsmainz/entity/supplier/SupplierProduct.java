package dev.yukado.systemsmainz.entity.supplier;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_products")
public class SupplierProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supplier; // z.B. "UNI_ELEKTRO"

    private String supplierSku;

    private String ean;

    private String title;

    @Column(length = 5000)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String attributesJson;

    private BigDecimal priceNet;

    private Integer stock;

    private LocalDateTime lastImport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierSku() {
        return supplierSku;
    }

    public void setSupplierSku(String supplierSku) {
        this.supplierSku = supplierSku;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributesJson() {
        return attributesJson;
    }

    public void setAttributesJson(String attributesJson) {
        this.attributesJson = attributesJson;
    }

    public BigDecimal getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(BigDecimal priceNet) {
        this.priceNet = priceNet;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getLastImport() {
        return lastImport;
    }

    public void setLastImport(LocalDateTime lastImport) {
        this.lastImport = lastImport;
    }
}

