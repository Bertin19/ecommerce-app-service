package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.ProductPurchaseRequest;
import de.bertin.ecommerce.controller.ProductPurchaseResponse;
import de.bertin.ecommerce.controller.ProductRequest;
import de.bertin.ecommerce.controller.ProductResponse;
import de.bertin.ecommerce.exceptions.ProductPurchaseException;
import de.bertin.ecommerce.model.Product;
import de.bertin.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public String createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        productRepository.save(product);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) {
        validateProductExistence(requests);

        return processPurchaseRequests(requests);
    }

    public ProductResponse findProductById(String productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductResponse> findProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    private void validateProductExistence(List<ProductPurchaseRequest> requests) {
        var productIds = requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products do(es) not exist");
        }
    }

    private List<ProductPurchaseResponse> processPurchaseRequests(List<ProductPurchaseRequest> requests) {
        var storedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (ProductPurchaseRequest productRequest : storedRequests) {
            var product = productRepository.findById(productRequest.productId())
                    .orElseThrow(() -> new ProductPurchaseException("Product not found: " + productRequest.productId()));

            checkStockAvailability(product, productRequest.quantity());

            updateProductQuantity(product, productRequest.quantity());

            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    private void checkStockAvailability(Product product, double requestedQuantity) {
        if (product.getAvailableQuantity() < requestedQuantity) {
            throw new ProductPurchaseException("Insufficient stock quantity for product: " + product.getId());
        }
    }

    private void updateProductQuantity(Product product, double quantityPurchased) {
        var newAvailableQuantity = product.getAvailableQuantity() - quantityPurchased;
        product.setAvailableQuantity(newAvailableQuantity);
        productRepository.save(product);
    }
}
