package dev.yukado.systemsmainz.service.product;

import dev.yukado.systemsmainz.dto.ProductCreateDto;
import dev.yukado.systemsmainz.entity.*;
import dev.yukado.systemsmainz.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductStockRepository stockRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(ProductCreateDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setSku(dto.getSku());
        product.setEan(dto.getEan());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setCategory(category);

        product.setWeightGrams(dto.getWeightGrams());
        product.setWidthMm(dto.getWidthMm());
        product.setHeightMm(dto.getHeightMm());
        product.setDepthMm(dto.getDepthMm());

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        product = productRepository.save(product);

        ProductPrice price = new ProductPrice();
        price.setProduct(product);
        price.setPriceGross(dto.getPriceGross());
        price.setVatRate(dto.getVatRate());
        price.setLastUpdate(LocalDateTime.now());
        priceRepository.save(price);

        ProductStock stock = new ProductStock();
        stock.setProduct(product);
        stock.setStock(dto.getStock());
        stock.setLastUpdate(LocalDateTime.now());
        stockRepository.save(stock);

        int pos = 1;
        for (String url : dto.getImageUrls()) {
            if (url == null || url.isBlank()) continue;

            ProductImage img = new ProductImage();
            img.setProduct(product);
            img.setUrl(url);
            img.setPosition(pos++);
            imageRepository.save(img);
        }

        return product;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Product> findPaginatedProducts(String search, Pageable pageable) {

        if (search != null && !search.isBlank()) {
            return productRepository
                    .findByTitleContainingIgnoreCaseOrSkuContainingIgnoreCaseOrEanContainingIgnoreCase(
                            search, search, search, pageable
                    );
        }

        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produkt nicht gefunden"));
    }
}
