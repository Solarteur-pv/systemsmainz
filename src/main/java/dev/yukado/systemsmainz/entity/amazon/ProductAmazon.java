package dev.yukado.systemsmainz.entity.amazon;


import dev.yukado.systemsmainz.entity.Product;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_amazon")
public class ProductAmazon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String amazonSku;

    private String asin;

    @Enumerated(EnumType.STRING)
    private ListingStatus listingStatus;

    private String lastFeedId;

    @Column(columnDefinition = "TEXT")
    private String lastError;

    private LocalDateTime lastSync;
}

