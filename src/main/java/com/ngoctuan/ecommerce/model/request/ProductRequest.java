package com.ngoctuan.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Create product request body")
public class ProductRequest {

    @NotEmpty(message = "Product name can not be null or empty")
    @Schema(description = "Product name", example = "Laptop")
    private String name;

    @NotNull(message = "Product price can not be null")
    @Schema(description = "Price of product", example = "1000.00")
    private BigDecimal price;

    @NotNull(message = "Stock quantity can not be null")
    @Schema(description = "Quantity of stock of product", example = "100")
    private Long stockQuantity;

}
