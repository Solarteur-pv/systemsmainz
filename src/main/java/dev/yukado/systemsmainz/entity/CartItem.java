package dev.yukado.systemsmainz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    // Preis zum Zeitpunkt des Hinzufügens
    private double priceNet;
    private double vatRate;   // z.B. 0.19

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPriceNet() { return priceNet; }
    public void setPriceNet(double priceNet) { this.priceNet = priceNet; }

    public double getVatRate() { return vatRate; }
    public void setVatRate(double vatRate) { this.vatRate = vatRate; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    // Berechnungen
    public double getTotalNet() {
        return priceNet * quantity;
    }

    public double getTotalVat() {
        return getTotalNet() * vatRate;
    }

    public double getTotalGross() {
        return getTotalNet() + getTotalVat();
    }
}

