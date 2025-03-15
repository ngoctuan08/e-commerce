package com.ngoctuan.ecommerce.repository;

import com.ngoctuan.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
