package com.ngoctuan.ecommerce.controller;

import com.ngoctuan.ecommerce.model.request.AddCouponRequest;
import com.ngoctuan.ecommerce.model.request.ProductRequest;
import com.ngoctuan.ecommerce.model.response.CommonResponse;
import com.ngoctuan.ecommerce.model.response.ProductResponse;
import com.ngoctuan.ecommerce.service.ProductService;
import com.ngoctuan.ecommerce.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "API for managing products")
public class ProductController {

    private final ProductService productService;

    //use to create a product
    @PostMapping("")
    @Operation(summary = "Create a product", description = "Create a product")
    public ResponseEntity<CommonResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        CommonResponse<ProductResponse> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.createProduct(productRequest));
        return ResponseEntity.ok(response);
    }

    //use to get list product
    @GetMapping("")
    @Operation(summary = "Get all products", description = "Get all products")
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProducts() {
        CommonResponse<List<ProductResponse>> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.getAllProducts());
        return ResponseEntity.ok(response);
    }

    //use to get detail of a product
    @GetMapping("/{id}")
    @Operation(summary = "Get detail of a products", description = "Get detail of a product")
    public ResponseEntity<CommonResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        CommonResponse<ProductResponse> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.findById(id));
        return ResponseEntity.ok(response);
    }

    //use to update product
    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update a product")
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        CommonResponse<ProductResponse> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.updateProduct(id, productRequest));
        return ResponseEntity.ok(response);
    }

    //use to remove a product
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product")
    public ResponseEntity<CommonResponse<Boolean>> deleteProduct(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.deleteProduct(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/add-coupon")
    @Operation(summary = "Add coupon to a product", description = "Add coupon to a product")
    public ResponseEntity<CommonResponse<Boolean>> addCoupon(@PathVariable Long id, @RequestBody @Valid AddCouponRequest addCouponRequest) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        response.setMessage(Constant.SUCCESS);
        response.setSuccess(true);
        response.setData(productService.addCoupon(id, addCouponRequest));
        return ResponseEntity.ok(response);
    }
}
