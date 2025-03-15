package com.ngoctuan.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Remove item request body")
public class RemoveItemRequest {

    @NotNull(message = "Order item can not be null")
    @Schema(description = "Order item id to remove", example = "1")
    private Long orderItemId;
}
