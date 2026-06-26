package dev.yukado.systemsmainz.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateDto {

    // Product
    private String sku;
    private String ean;
    private String title;
    private String description;
    private String brand;
    private Long categoryId;

    private Integer weightGrams;
    private Integer widthMm;
    private Integer heightMm;
    private Integer depthMm;

    // Price
    private BigDecimal priceGross;
    private BigDecimal vatRate;

    // Stock
    private Integer stock;

    // Images (URLs)
    private List<String> imageUrls;
}

