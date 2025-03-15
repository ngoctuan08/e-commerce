package com.ngoctuan.ecommerce.controller;

import com.ngoctuan.ecommerce.model.response.CommonResponse;
import com.ngoctuan.ecommerce.model.response.ProductResponse;
import com.ngoctuan.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any())).thenReturn(new ProductResponse());

        ResponseEntity<CommonResponse<ProductResponse>> result = productController.createProduct(null);
        HttpStatusCode statusCode = result.getStatusCode();

        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        ResponseEntity<CommonResponse<List<ProductResponse>>> result = productController.getAllProducts();
        HttpStatusCode statusCode = result.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testGetProductById() {
        when(productService.findById(anyLong())).thenReturn(new ProductResponse());
        ResponseEntity<CommonResponse<ProductResponse>> result = productController.getProduct(1L);
        HttpStatusCode statusCode = result.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(anyLong(), any())).thenReturn(new ProductResponse());
        ResponseEntity<CommonResponse<ProductResponse>> result = productController.updateProduct(1L, null);
        HttpStatusCode statusCode = result.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testDeleteProduct() {
        when(productService.deleteProduct(anyLong())).thenReturn(true);
        ResponseEntity<CommonResponse<Boolean>> result = productController.deleteProduct(1L);
        HttpStatusCode statusCode = result.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testAddCoupon() {
        when(productService.addCoupon(anyLong(), any())).thenReturn(true);
        ResponseEntity<CommonResponse<Boolean>> result = productController.addCoupon(1L, null);
        HttpStatusCode statusCode = result.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
    }
}
