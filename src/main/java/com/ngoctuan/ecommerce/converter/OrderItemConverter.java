package com.ngoctuan.ecommerce.converter;

import com.ngoctuan.ecommerce.entity.OrderItem;
import com.ngoctuan.ecommerce.model.request.AddItemRequest;
import com.ngoctuan.ecommerce.model.response.OrderItemResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemConverter {

    public OrderItem from(AddItemRequest request) {
        return OrderItem.builder()
                .quantity(request.getQuantity())
                .productId(request.getProductId())
                .build();
    }

    public OrderItemResponse to(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .discountApplied(orderItem.getDiscountApplied())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getFinalPrice())
                .productId(orderItem.getProductId())
                .build();
    }
}
