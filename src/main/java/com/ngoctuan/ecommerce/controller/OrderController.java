package com.ngoctuan.ecommerce.controller;

import com.ngoctuan.ecommerce.model.request.AddItemRequest;
import com.ngoctuan.ecommerce.model.request.RemoveItemRequest;
import com.ngoctuan.ecommerce.model.response.CommonResponse;
import com.ngoctuan.ecommerce.model.response.OrderResponse;
import com.ngoctuan.ecommerce.service.OrderService;
import com.ngoctuan.ecommerce.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "API for managing orders")
public class OrderController {

    private final OrderService orderService;

    // use to add item to order
    @PostMapping("/{orderId}/add-item")
    @Operation(summary = "Add order item to order", description = "Add order item to order")
    public ResponseEntity<CommonResponse<Boolean>> addItem(@RequestBody @Valid List<AddItemRequest> addItemRequests, @PathVariable Long orderId) {
        CommonResponse<Boolean> commonResponse = new CommonResponse<>();
        commonResponse.setMessage(Constant.SUCCESS);
        commonResponse.setSuccess(true);
        commonResponse.setData(orderService.addItem(addItemRequests, orderId));
        return ResponseEntity.ok(commonResponse);
    }

    // use to remove item from order
    @PostMapping("/{orderId}/remove-item")
    @Operation(summary = "Remove order item from order", description = "Remove order item from order")
    public ResponseEntity<CommonResponse<Boolean>> removeItem(@RequestBody @Valid List<RemoveItemRequest> removeItemRequests, @PathVariable Long orderId) {
        CommonResponse<Boolean> commonResponse = new CommonResponse<>();
        commonResponse.setMessage(Constant.SUCCESS);
        commonResponse.setSuccess(true);
        commonResponse.setData(orderService.removeItem(removeItemRequests, orderId));
        return ResponseEntity.ok(commonResponse);
    }

    // use to check out order
    @PostMapping("{orderId}/checkout")
    @Operation(summary = "Checkout an order", description = "Checkout an order")
    public ResponseEntity<CommonResponse<OrderResponse>> checkout(@PathVariable Long orderId) {
        CommonResponse<OrderResponse> commonResponse = new CommonResponse<>();
        commonResponse.setMessage(Constant.SUCCESS);
        commonResponse.setSuccess(true);
        commonResponse.setData(orderService.checkout(orderId));
        return ResponseEntity.ok(commonResponse);
    }
}
