package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.Product;
import dev.yukado.systemsmainz.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    Optional<ProductPrice> findByProduct(Product product);
}


