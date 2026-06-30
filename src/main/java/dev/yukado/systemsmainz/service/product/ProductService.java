package dev.yukado.systemsmainz.service.product;

import dev.yukado.systemsmainz.dto.ProductCreateDto;
import dev.yukado.systemsmainz.entity.Category;
import dev.yukado.systemsmainz.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductCreateDto dto);

    List<Category> getAllCategories();

    Page<Product> findPaginatedProducts(String search, Pageable pageable);

    List<Product> findAll();

    Page<Product> findPaginatedProducts(Pageable pageable);

    Product findById(Long id);
}
