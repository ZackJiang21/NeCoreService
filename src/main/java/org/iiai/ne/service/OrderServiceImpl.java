package org.iiai.ne.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.bcel.Const;
import org.iiai.ne.dao.*;
import org.iiai.ne.exception.BadRequestException;
import org.iiai.ne.exception.HttpNotFoundException;
import org.iiai.ne.model.*;
import org.iiai.ne.util.ConstantUtil;
import org.iiai.ne.util.EventUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private CouponDao couponDao;

    @Override
    @Transactional
    public void createOrder(int userId, OrderDetail orderInfo) {
        List<Dish> dishes = validateDishes(orderInfo);
        Address address = validateAddress(userId, orderInfo);
        int storeId = validateStore(orderInfo);
        Coupon coupon = validateCoupon(userId, orderInfo, storeId);

        Map<Integer, Integer> orderCountMap = orderInfo.getDishes()
                .stream()
                .collect(Collectors.toMap(Dish::getId, dish -> dish.getCount(), (k1, k2) -> k1));
        dishes.stream()
                .forEach(dish -> {
                    if (dish.getStore() != storeId) {
                        throw new BadRequestException();
                    }
                    dish.setCount(orderCountMap.get(dish.getId()));
                });
        int originPrice = dishes.stream().mapToInt(dish -> dish.getPrice() * orderCountMap.get(dish.getId())).sum();
        int finalPrice = getFinalPrice(coupon, originPrice);

        OrderDetail order = new OrderDetail();
        order.setUserId(userId);
        order.setStore(new Store(storeId));
        order.setAddress(address);
        order.setOriginPrice(originPrice);
        order.setFinalPrice(finalPrice);
        order.setRemark(orderInfo.getRemark());
        if (coupon != null) {
            order.setIsDiscount(true);
            order.setCoupon(coupon);
        }

        orderDao.insertOrder(order);
        orderDao.insertOrderDetail(order.getId(), dishes);
        if (coupon != null) {
            couponDao.updateCouponStatus(coupon.getId(), ConstantUtil.CouponStatus.IS_USED);
        }
        sendAddOrderEvent(order);

    }

    private void sendAddOrderEvent(OrderDetail order) {
        KafkaEvent event = new KafkaEvent(ConstantUtil.KafkaEvent.Type.ADD, JSON.toJSONString(order));
        kafkaTemplate.send(ConstantUtil.KafkaEvent.ORDER_EVENT, JSON.toJSONString(event));
    }

    private int getFinalPrice(Coupon coupon, int originPrice) {
        int finalPrice;
        if (coupon != null) {
            finalPrice = coupon.getValue() > originPrice ? 0 : originPrice - coupon.getValue();
        } else {
            finalPrice = originPrice;
        }
        return finalPrice;
    }

    private Coupon validateCoupon(int userId, OrderDetail orderInfo, int storeId) {
        Coupon ret = null;
        Coupon coupon = orderInfo.getCoupon();
        if (coupon != null) {
            ret = couponDao.getCouponById(coupon.getId());
            if (ret == null) {
                LOGGER.error("CouponId {} not exist", coupon.getId());
                throw new BadRequestException();
            }
            if (ret.getUserId() != userId || ret.getStore().getId() != storeId) {
                LOGGER.error("request:  userID {} storeID {}, coupon: userId {} storeId {}", userId, storeId, ret.getUserId(), ret.getStore().getId());
                throw new BadRequestException();
            }
            if (ret.getStatus() != ConstantUtil.CouponStatus.IS_CLAIMED) {
                LOGGER.error("couponId {} not avaliable");
            }
        }
        return ret;
    }

    private int validateStore(OrderDetail orderInfo) {
        int storeId = orderInfo.getStore().getId();
        Store store = storeDao.getStoreInfoById(storeId);
        if (store == null) {
            LOGGER.error("store id {} not exist", storeId);
            throw new HttpNotFoundException();
        }
        if (!store.getIsOpen()) {
            LOGGER.error("store id {} already closed", storeId);
            throw new BadRequestException("Store already closed");
        }
        return storeId;
    }

    private Address validateAddress(int userId, OrderDetail orderInfo) {
        Address address = addressDao.getAddressById(orderInfo.getAddress().getId(), userId);
        if (address == null) {
            LOGGER.error("address not found");
            throw new HttpNotFoundException("Address not found");
        }
        return address;
    }

    private List<Dish> validateDishes(OrderDetail orderInfo) {
        Set<Integer> dishIds = orderInfo.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toSet());
        List<Dish> dishes = dishDao.getDishByIds(dishIds);
        if (dishes.size() != dishIds.size()) {
            LOGGER.error("dish not found");
            throw new HttpNotFoundException("Some of the dishes cannot found");
        }
        return dishes;
    }


    @Override
    public PaginationData<Order> getOrderInfoByUserId(int userId, int pageSize, int sinceId) {
        List<Order> orderList = orderDao.getOrderListByUserId(userId, sinceId, pageSize);
        int leftCount = orderDao.getLeftCount(userId, sinceId);

        PaginationData<Order> result = new PaginationData<>();
        result.setData(orderList);
        result.setHasMore(orderList.size() < leftCount);

        return result;
    }

    @Override
    public OrderDetail getOrderById(int orderId) {
        OrderDetail order = orderDao.getOrderById(orderId);
        if (order == null) {
            throw new HttpNotFoundException();
        }
        return order;
    }

}
