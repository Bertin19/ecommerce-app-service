package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.ProductPurchaseResponse;
import de.bertin.ecommerce.controller.ProductRequest;
import de.bertin.ecommerce.controller.ProductResponse;
import de.bertin.ecommerce.model.Category;
import de.bertin.ecommerce.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .description(request.description())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
