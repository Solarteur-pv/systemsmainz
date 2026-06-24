package dev.yukado.systemsmainz.repository;


import dev.yukado.systemsmainz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySku(String sku);

    Product findBySku(String sku);
}

