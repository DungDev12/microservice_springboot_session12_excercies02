package com.exam.productserver.service;

import com.exam.productserver.dto.request.UpdateProductRequest;
import com.exam.productserver.entity.Product;
import com.exam.productserver.repository.ProductRepository;
import com.exam.productserver.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("Đọc từ Database: " + id);
        return getById(id);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public Product updateProductById(Long id, UpdateProductRequest request) {
        Product product = getById(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setStock(request.stock());

        return productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "products", key = "'all'")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sản phẩm"));
    }
}
