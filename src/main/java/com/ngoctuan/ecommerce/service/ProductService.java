package com.ngoctuan.ecommerce.service;

import com.ngoctuan.ecommerce.model.request.AddCouponRequest;
import com.ngoctuan.ecommerce.model.request.ProductRequest;
import com.ngoctuan.ecommerce.model.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse findById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    Boolean deleteProduct(Long id);

    List<ProductResponse> getAllProducts();

    Boolean addCoupon(Long id, AddCouponRequest addCouponRequest);
}
