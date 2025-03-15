package com.ngoctuan.ecommerce.service;

import com.ngoctuan.ecommerce.entity.Coupon;
import com.ngoctuan.ecommerce.entity.CouponType;
import com.ngoctuan.ecommerce.entity.Order;
import com.ngoctuan.ecommerce.entity.OrderItem;
import com.ngoctuan.ecommerce.entity.Product;
import com.ngoctuan.ecommerce.exception.BadRequestException;
import com.ngoctuan.ecommerce.exception.ResourceNotFoundException;
import com.ngoctuan.ecommerce.model.request.AddItemRequest;
import com.ngoctuan.ecommerce.model.request.RemoveItemRequest;
import com.ngoctuan.ecommerce.repository.CouponRepository;
import com.ngoctuan.ecommerce.repository.OrderItemRepository;
import com.ngoctuan.ecommerce.repository.OrderRepository;
import com.ngoctuan.ecommerce.repository.ProductRepository;
import com.ngoctuan.ecommerce.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CouponRepository couponRepository;

    private Order order;
    private Product product;
    private Coupon coupon;
    private AddItemRequest addItemRequest;
    private OrderItem orderItem;
    private RemoveItemRequest removeItemRequest;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setTotalPrice(BigDecimal.ZERO);

        product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal("100"));
        product.setStockQuantity(100L);

        coupon = new Coupon();
        coupon.setId(1L);
        coupon.setCouponType(CouponType.FIXED);
        coupon.setDiscount(new BigDecimal("10"));

        addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1L);
        addItemRequest.setQuantity(2L);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrderId(1L);
        orderItem.setProductId(1L);
        orderItem.setFinalPrice(new BigDecimal("100"));

        removeItemRequest = new RemoveItemRequest();
        removeItemRequest.setOrderItemId(1L);
    }

    @Test
    void testAddItem_Success_NoCoupon() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));

        boolean result = orderService.addItem(List.of(addItemRequest), 1L);

        assertTrue(result);
        verify(orderRepository, times(2)).save(order);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testAddItem_Success_WithFixedCoupon() {
        product.setCouponId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

        boolean result = orderService.addItem(List.of(addItemRequest), 1L);

        assertTrue(result);
        verify(orderRepository, times(2)).save(order);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testAddItem_Success_WithRateCoupon() {
        coupon.setCouponType(CouponType.RATE);
        product.setCouponId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

        boolean result = orderService.addItem(List.of(addItemRequest), 1L);

        assertTrue(result);
        verify(orderRepository, times(2)).save(order);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testAddItem_Failure_WithOverQuantity() {
        product.setStockQuantity(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(BadRequestException.class, () -> orderService.addItem(List.of(addItemRequest), 1L));

        assertEquals("Order quantity of product with id: 1 is greater than current quantity in stock", exception.getMessage());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testAddItem_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.addItem(List.of(addItemRequest), 1L);
        });

        assertEquals("Order not found with id 1", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testAddItem_ProductNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findProductById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.addItem(List.of(addItemRequest), 1L);
        });

        assertEquals("Product not found with id 1", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void testRemoveItem_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(productRepository.findProductById(1L)).thenReturn(Optional.of(product));

        boolean result = orderService.removeItem(List.of(removeItemRequest), 1L);

        assertTrue(result);
        assertEquals(new BigDecimal("-100"), order.getTotalPrice());

        verify(orderItemRepository, times(1)).delete(orderItem);
        verify(orderRepository, times(2)).save(order);
    }

    @Test
    void testRemoveItem_OrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.removeItem(List.of(removeItemRequest), 1L);
        });

        assertEquals("Order not found with id 1", exception.getMessage());
        verify(orderItemRepository, never()).delete(any());
    }

    @Test
    void testRemoveItem_OrderItemNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.removeItem(List.of(removeItemRequest), 1L);
        });

        assertEquals("OrderItem not found with id 1", exception.getMessage());
        verify(orderItemRepository, never()).delete(any());
    }

    @Test
    void testRemoveItem_ProductNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(productRepository.findProductById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.removeItem(List.of(removeItemRequest), 1L);
        });

        assertEquals("Product not found with id 1", exception.getMessage());
        verify(orderItemRepository, never()).delete(any());
    }

    @Test
    void testCheckout_Success() {
        order.setOrderDate(LocalDateTime.now());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrderId(1L)).thenReturn(Collections.singletonList(orderItem));

        assertNotNull(orderService.checkout(1L));
    }
}
