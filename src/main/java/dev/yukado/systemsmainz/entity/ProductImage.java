package dev.yukado.systemsmainz.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Produkt, zu dem das Bild gehört
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Amazon verlangt eine öffentliche HTTPS-URL
    @Column(nullable = false)
    private String url;

    // Reihenfolge der Bilder (1 = Hauptbild)
    private Integer position;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}

