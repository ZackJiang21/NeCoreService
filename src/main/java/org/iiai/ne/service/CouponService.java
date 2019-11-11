package org.iiai.ne.service;

import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.PaginationData;

public interface CouponService {
    PaginationData<Coupon> getCouponByUserId(int userId, int storeId, int pageSize, int sinceId);
}
