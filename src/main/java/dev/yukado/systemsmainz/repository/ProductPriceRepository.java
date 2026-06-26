package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}

