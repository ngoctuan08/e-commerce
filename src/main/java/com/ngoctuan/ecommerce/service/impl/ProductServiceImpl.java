package com.ngoctuan.ecommerce.service.impl;

import com.ngoctuan.ecommerce.converter.ProductConverter;
import com.ngoctuan.ecommerce.entity.Product;
import com.ngoctuan.ecommerce.exception.ResourceNotFoundException;
import com.ngoctuan.ecommerce.model.request.AddCouponRequest;
import com.ngoctuan.ecommerce.model.request.ProductRequest;
import com.ngoctuan.ecommerce.model.response.ProductResponse;
import com.ngoctuan.ecommerce.repository.ProductRepository;
import com.ngoctuan.ecommerce.service.ProductService;
import com.ngoctuan.ecommerce.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = ProductConverter.to(productRequest);
        return ProductConverter.from(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse findById(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + id));
        return ProductConverter.from(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + id));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        return ProductConverter.from(productRepository.save(product));
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + id));
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductConverter::from).toList();
    }

    @Override
    @Transactional
    public Boolean addCoupon(Long id, AddCouponRequest addCouponRequest) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + id));
        product.setCouponId(addCouponRequest.getCouponId());
        productRepository.save(product);
        return true;
    }
}
