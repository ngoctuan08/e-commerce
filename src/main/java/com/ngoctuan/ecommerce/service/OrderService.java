package com.ngoctuan.ecommerce.service;

import com.ngoctuan.ecommerce.model.request.AddItemRequest;
import com.ngoctuan.ecommerce.model.request.RemoveItemRequest;
import com.ngoctuan.ecommerce.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    Boolean addItem(List<AddItemRequest> addItemRequests, Long orderId);

    Boolean removeItem(List<RemoveItemRequest> addItemRequests, Long orderId);

    OrderResponse checkout(Long orderId);
}
