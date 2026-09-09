package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setCategory(product.getCategory());
        existing.setBrand(product.getBrand());
        existing.setStock(product.getStock());
        existing.setPrice(product.getPrice());
        existing.setDiscountType(product.getDiscountType());

        if (product.getDetail() != null) {
            if (existing.getDetail() == null) {
                existing.setDetail(product.getDetail());
            } else {
                existing.getDetail().setDescription(product.getDetail().getDescription());
                existing.getDetail().setWarranty(product.getDetail().getWarranty());
                existing.getDetail().setWeight(product.getDetail().getWeight());
                existing.getDetail().setDimensions(product.getDetail().getDimensions());
                existing.getDetail().setManufacturedCountry(product.getDetail().getManufacturedCountry());
            }
        }
        return productRepository.save(existing);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product addReview(Long productId, Review review) {
        Product product = getProductById(productId);
        product.addReview(review);
        return productRepository.save(product);
    }
}
