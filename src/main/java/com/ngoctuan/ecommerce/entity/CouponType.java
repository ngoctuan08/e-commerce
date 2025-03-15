package com.ngoctuan.ecommerce.entity;

import lombok.Getter;

@Getter
public enum CouponType {

    FIXED("$"),
    RATE("%");

    private final String amount;

    CouponType(String amount) {
        this.amount = amount;
    }
}
