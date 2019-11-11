package org.iiai.ne.service;

import org.iiai.ne.model.Order;
import org.iiai.ne.model.OrderDetail;
import org.iiai.ne.model.PaginationData;

public interface OrderService {
    void createOrder(int userId, OrderDetail orderInfo);

    PaginationData<Order> getOrderInfoByUserId(int userId, int pageSize, int sinceId);

    OrderDetail getOrderById(int orderId);
}
