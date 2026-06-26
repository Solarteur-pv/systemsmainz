package dev.yukado.systemsmainz.repository;


import dev.yukado.systemsmainz.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    Product findBySku(String sku);

        // Suche nach Titel, SKU oder EAN
        Page<Product> findByTitleContainingIgnoreCaseOrSkuContainingIgnoreCaseOrEanContainingIgnoreCase(
                String title, String sku, String ean, Pageable pageable
        );

}

