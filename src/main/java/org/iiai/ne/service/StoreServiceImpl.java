package org.iiai.ne.service;

import org.iiai.ne.dao.ActivityDao;
import org.iiai.ne.dao.CouponDao;
import org.iiai.ne.dao.OrderDao;
import org.iiai.ne.dao.StoreDao;
import org.iiai.ne.exception.HttpNotFoundException;
import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.Store;
import org.iiai.ne.model.StoreActivity;
import org.iiai.ne.model.StoreDetail;
import org.iiai.ne.util.BatchOperUtil;
import org.iiai.ne.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CouponDao couponDao;


    @Override
    public StoreDetail getStoreInfoById(int id) {
        StoreDetail storeInfo = storeDao.getStoreInfoById(id);
        if (storeInfo == null) {
            LOGGER.error("store id {} not found", id);
            throw new HttpNotFoundException();
        }
        return storeInfo;
    }

    @Override
    @Transactional
    public Coupon getCouponInStore(int userId, int storeId) {
        List<StoreActivity> activities = activityDao.getActivitiesByStoreId(storeId);
        for (StoreActivity activity : activities) {
            if (activity.getType() == ConstantUtil.ActivityType.GIFT) {
                int satisfyVal = activity.getSatisfyValue();
                List<Integer> orderIds = orderDao.getNotRedeemOrderIds(userId, storeId);
                if (orderIds.size() == satisfyVal) {
                    LOGGER.info("userId {} redeem coupon, storeId {}", userId, storeId);
                    orderDao.updateOrderRedeem(orderIds);

                    Coupon coupon = new Coupon();
                    coupon.setUserId(userId);
                    coupon.setValue(activity.getReturnValue());
                    coupon.setStore(new Store(storeId));
                    coupon.setStatus(ConstantUtil.CouponStatus.IS_CLAIMED);

                    couponDao.insertCoupon(coupon);
                    return coupon;
                }
            }
        }
        return null;
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    protected void updateStoreStatus() {
        LOGGER.info("Updating Store status");
        BatchOperUtil.executeWithFetcher(128,
                (BatchOperUtil.DataFetcher<StoreDetail>) (start, limit) -> storeDao.getStoresInfo(start, limit),
                (BatchOperUtil.Operator<StoreDetail>) data -> {
                    if (data.isEmpty()) {
                        return;
                    }
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    List<Store> stores = new ArrayList<>();
                    for (StoreDetail storeInfo : data) {
                        boolean isOpen;
                        if (isStoreOpen(hour, storeInfo)) {
                            isOpen = true;
                        } else {
                            isOpen = false;
                        }
                        storeInfo.setIsOpen(isOpen);
                        stores.add(storeInfo);
                    }
                    storeDao.updateStoreStatus(stores);
                });
    }

    private boolean isStoreOpen(int hour, StoreDetail storeInfo) {
        if ((hour >= storeInfo.getStartAm() && hour < storeInfo.getEndAm()) ||
                hour >= storeInfo.getStartPm() && hour < storeInfo.getEndPm()) {
            return true;
        } else {
            return false;
        }
    }
}
