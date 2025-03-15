package com.ngoctuan.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Add item request body")
public class AddItemRequest {

    @NotNull(message = "Product id can not be null or empty")
    @Schema(description = "Product id to add", example = "1")
    private Long productId;

    @NotNull(message = "Quantity can not be null or empty")
    @Schema(description = "Quantity of product to add", example = "20")
    private Long quantity;
}
