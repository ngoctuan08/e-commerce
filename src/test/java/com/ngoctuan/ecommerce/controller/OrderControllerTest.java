package com.ngoctuan.ecommerce.controller;

import com.ngoctuan.ecommerce.model.response.CommonResponse;
import com.ngoctuan.ecommerce.model.response.OrderResponse;
import com.ngoctuan.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testAddItem() {
        when(orderService.addItem(any(), anyLong())).thenReturn(Boolean.TRUE);

        ResponseEntity<CommonResponse<Boolean>> result = orderController.addItem(null, 1L);
        HttpStatusCode statusCode = result.getStatusCode();

        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testRemoveItem() {
        when(orderService.removeItem(any(), anyLong())).thenReturn(Boolean.TRUE);

        ResponseEntity<CommonResponse<Boolean>> result = orderController.removeItem(null, 1L);
        HttpStatusCode statusCode = result.getStatusCode();

        assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void testCheckoutItem() {
        when(orderService.checkout(anyLong())).thenReturn(new OrderResponse());

        ResponseEntity<CommonResponse<OrderResponse>> result = orderController.checkout(1L);
        HttpStatusCode statusCode = result.getStatusCode();

        assertEquals(HttpStatus.OK, statusCode);
    }
}
