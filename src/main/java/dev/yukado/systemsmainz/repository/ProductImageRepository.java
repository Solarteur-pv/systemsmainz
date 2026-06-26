package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}

