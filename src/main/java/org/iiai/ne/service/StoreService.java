package org.iiai.ne.service;

import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.StoreDetail;

public interface StoreService {
    StoreDetail getStoreInfoById(int id);

    Coupon getCouponInStore(int userId, int storeId);
}
