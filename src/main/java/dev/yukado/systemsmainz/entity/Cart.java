package dev.yukado.systemsmainz.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public int getTotalQuantity() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public double getTotalNet() {
        return items.stream().mapToDouble(CartItem::getTotalNet).sum();
    }

    public double getTotalVat() {
        return items.stream().mapToDouble(CartItem::getTotalVat).sum();
    }

    public double getTotalGross() {
        return items.stream().mapToDouble(CartItem::getTotalGross).sum();
    }
}

