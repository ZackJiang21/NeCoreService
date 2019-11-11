package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.Coupon;

import java.util.List;

@Mapper
public interface CouponDao {
    void insertCoupon(@Param("coupon") Coupon coupon);

    List<Coupon> getCouponListByUserId(@Param("userId") int userId, @Param("storeId") int storeId, @Param("sinceId") int sinceId, @Param("limit") int limit, @Param("status") int status);

    int getLeftCount(@Param("userId") int userId, @Param("storeId") int storeId, @Param("sinceId") int sinceId, @Param("status") int status);

    Coupon getCouponById(@Param("couponId") int couponId);

    void updateCouponStatus(@Param("couponId") int couponId, @Param("status") int status);
}
