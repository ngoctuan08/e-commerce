package com.ngoctuan.ecommerce.converter;

import com.ngoctuan.ecommerce.entity.Product;
import com.ngoctuan.ecommerce.model.request.ProductRequest;
import com.ngoctuan.ecommerce.model.response.ProductResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {
    public static Product to(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .build();
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .couponId(product.getCouponId())
                .build();
    }
}
