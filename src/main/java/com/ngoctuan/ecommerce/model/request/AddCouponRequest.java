package com.ngoctuan.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Add coupon request body")
public class AddCouponRequest {

    @NotNull(message = "Coupon can not be null")
    @Schema(description = "Coupon id to add", example = "1")
    private Long couponId;
}
