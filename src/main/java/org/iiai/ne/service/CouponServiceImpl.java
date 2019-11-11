package org.iiai.ne.service;

import org.iiai.ne.dao.CouponDao;
import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.PaginationData;
import org.iiai.ne.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponDao couponDao;

    @Override
    public PaginationData<Coupon> getCouponByUserId(int userId, int storeId, int pageSize, int sinceId) {
        List<Coupon> coupons = couponDao.getCouponListByUserId(userId, storeId, sinceId, pageSize, ConstantUtil.CouponStatus.IS_CLAIMED);
        int leftCount = couponDao.getLeftCount(userId, storeId, sinceId, ConstantUtil.CouponStatus.IS_CLAIMED);

        PaginationData<Coupon> result = new PaginationData<>();
        result.setData(coupons);
        result.setHasMore(coupons.size() < leftCount);
        return result;
    }
}
