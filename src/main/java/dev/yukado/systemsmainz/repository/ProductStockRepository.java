package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}

