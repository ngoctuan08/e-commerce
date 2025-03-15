package com.ngoctuan.ecommerce.service.impl;

import com.ngoctuan.ecommerce.converter.OrderItemConverter;
import com.ngoctuan.ecommerce.entity.Coupon;
import com.ngoctuan.ecommerce.entity.CouponType;
import com.ngoctuan.ecommerce.entity.Order;
import com.ngoctuan.ecommerce.entity.OrderItem;
import com.ngoctuan.ecommerce.entity.Product;
import com.ngoctuan.ecommerce.exception.BadRequestException;
import com.ngoctuan.ecommerce.exception.ResourceNotFoundException;
import com.ngoctuan.ecommerce.model.request.AddItemRequest;
import com.ngoctuan.ecommerce.model.request.RemoveItemRequest;
import com.ngoctuan.ecommerce.model.response.OrderItemResponse;
import com.ngoctuan.ecommerce.model.response.OrderResponse;
import com.ngoctuan.ecommerce.repository.CouponRepository;
import com.ngoctuan.ecommerce.repository.OrderItemRepository;
import com.ngoctuan.ecommerce.repository.OrderRepository;
import com.ngoctuan.ecommerce.repository.ProductRepository;
import com.ngoctuan.ecommerce.service.OrderService;
import com.ngoctuan.ecommerce.util.Constant;
import com.ngoctuan.ecommerce.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final CouponRepository couponRepository;

    @Override
    @Transactional(rollbackFor = { ResourceNotFoundException.class, BadRequestException.class })
    public Boolean addItem(List<AddItemRequest> addItemRequests, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ORDER_NOT_FOUND + orderId));

        addItemRequests.forEach(orderItemRequest -> calculateTotalPrice(orderItemRequest, order));
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        return true;
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public Boolean removeItem(List<RemoveItemRequest> removeItemRequests, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ORDER_NOT_FOUND + orderId));

        removeItemRequests.forEach(removeItemRequest -> {
            OrderItem orderItem = orderItemRepository.findById(removeItemRequest.getOrderItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.ORDER_ITEM_NOT_FOUND + removeItemRequest.getOrderItemId()));

            Product product = productRepository.findProductById(orderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + orderItem.getProductId()));

            order.setTotalPrice(order.getTotalPrice().subtract(orderItem.getFinalPrice()));

            orderItemRepository.delete(orderItem);
            orderRepository.save(order);
            updateProduct(product, orderItem);
        });

        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        return true;
    }

    @Override
    public OrderResponse checkout(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.ORDER_NOT_FOUND + orderId));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        List<OrderItemResponse> orderItemResponses = orderItems.stream().map(OrderItemConverter::to).toList();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setOrderDate(DateUtil.fromDateToString(order.getOrderDate()));
        orderResponse.setOrderItems(orderItemResponses);
        return orderResponse;
    }

    private void calculateTotalPrice(AddItemRequest addItemRequest, Order order) {
        OrderItem orderItem = OrderItemConverter.from(addItemRequest);
        Product product = productRepository.findProductById(addItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + addItemRequest.getProductId()));

        BigDecimal productPrice = product.getPrice();
        BigDecimal discountApplied;
        BigDecimal finalPrice;

        if (Objects.isNull(product.getCouponId())) {
            discountApplied = BigDecimal.ZERO;
            finalPrice = productPrice.multiply(new BigDecimal(addItemRequest.getQuantity()));
            order.setTotalPrice(order.getTotalPrice().add(finalPrice));
        } else {
            Coupon coupon = couponRepository.findById(product.getCouponId())
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.COUPON_NOT_FOUND + product.getCouponId()));
            if (coupon.getCouponType() == CouponType.FIXED) {
                discountApplied = coupon.getDiscount().multiply(new BigDecimal(addItemRequest.getQuantity()));
            } else {
                discountApplied = coupon.getDiscount().divide(new BigDecimal(100)).multiply(productPrice).multiply(new BigDecimal(addItemRequest.getQuantity()));
            }
            finalPrice = productPrice.subtract(discountApplied).multiply(new BigDecimal(addItemRequest.getQuantity()));
            order.setTotalPrice(order.getTotalPrice().add(finalPrice));
        }

        orderRepository.save(order);

        updateProduct(product, addItemRequest);
        orderItem.setOrderId(order.getId());
        orderItem.setDiscountApplied(discountApplied);
        orderItem.setFinalPrice(finalPrice);
        orderItemRepository.save(orderItem);
    }

    private void updateProduct(Product product, AddItemRequest addItemRequest) {
        if (product.getStockQuantity() < addItemRequest.getQuantity()) {
            throw new BadRequestException("Order quantity of product with id: " +
                    addItemRequest.getProductId() + " is greater than current quantity in stock");
        }

        product.setStockQuantity(product.getStockQuantity() - addItemRequest.getQuantity());
        productRepository.save(product);
    }

    private void updateProduct(Product product, OrderItem orderItem) {
        product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
        productRepository.save(product);
    }

}
