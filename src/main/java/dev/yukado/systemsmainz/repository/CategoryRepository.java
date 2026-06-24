package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
