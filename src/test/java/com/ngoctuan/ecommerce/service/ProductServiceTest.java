package com.ngoctuan.ecommerce.service;

import com.ngoctuan.ecommerce.entity.Product;
import com.ngoctuan.ecommerce.model.request.AddCouponRequest;
import com.ngoctuan.ecommerce.model.request.ProductRequest;
import com.ngoctuan.ecommerce.model.response.ProductResponse;
import com.ngoctuan.ecommerce.repository.ProductRepository;
import com.ngoctuan.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("test");
        product.setPrice(new BigDecimal("100.00"));

        productRequest = new ProductRequest();
        productRequest.setName("test");
        productRequest.setPrice(new BigDecimal("100.00"));
    }

    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse result = productService.createProduct(productRequest);

        assertNotNull(result);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void findProduct() {
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        assertNotNull(productService.findById(1L));
    }

    @Test
    void updateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));

        ProductResponse result = productService.updateProduct(1L, productRequest);
        assertNotNull(result);
    }

    @Test
    void deleteProduct() {
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void findAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        List<ProductResponse> result = productService.getAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void addCoupon() {
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        AddCouponRequest addCouponRequest = new AddCouponRequest();
        addCouponRequest.setCouponId(1L);

        Boolean result = productService.addCoupon(1L, addCouponRequest);
        assertTrue(result);
        verify(productRepository, times(1)).save(product);
    }
}
