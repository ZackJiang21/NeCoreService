package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.Dish;
import org.iiai.ne.model.Order;
import org.iiai.ne.model.OrderDetail;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {
    List<Order> getOrderListByUserId(@Param("userId") int userId, @Param("sinceId") int sinceId, @Param("limit") int limit);

    int getLeftCount(@Param("userId") int userId, @Param("sinceId") int sinceId);

    void insertOrder(@Param("order") OrderDetail order);

    void insertOrderDetail(@Param("orderId") int orderId, @Param("dishes") List<Dish> dishes);

    OrderDetail getOrderById(@Param("orderId") int orderId);

    List<Integer> getNotRedeemOrderIds(@Param("userId") int userId, @Param("storeId") int storeId);

    void updateOrderRedeem(@Param("orderIds") List<Integer> orderIds);
}
