package org.iiai.ne.controller;

import org.iiai.ne.aop.Privilege;
import org.iiai.ne.exception.BadRequestException;
import org.iiai.ne.exception.NotPermittedException;
import org.iiai.ne.model.Order;
import org.iiai.ne.model.OrderDetail;
import org.iiai.ne.model.PaginationData;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.OrderService;
import org.iiai.ne.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    @Privilege
    public Response createOrder(HttpServletRequest request, @RequestBody OrderDetail orderInfo) {
        int userId = TokenUtil.getUserId(request);
        if (CollectionUtils.isEmpty(orderInfo.getDishes()) || orderInfo.getRemark().length() > 32
                || orderInfo.getAddress() == null || orderInfo.getStore() == null) {
            throw new BadRequestException();
        }
        orderService.createOrder(userId, orderInfo);
        return new Response().success();
    }

    @RequestMapping(method = RequestMethod.GET)
    @Privilege
    public Response getOrderInfoByUserId(HttpServletRequest request, @RequestParam int pageSize, @RequestParam int sinceId) {
        int userId = TokenUtil.getUserId(request);
        if (pageSize <= 0 || sinceId < 0) {
            throw new BadRequestException();
        }
        PaginationData<Order> result = orderService.getOrderInfoByUserId(userId, pageSize, sinceId);
        return new Response().success(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @Privilege
    public Response getOrderById(HttpServletRequest request, @PathVariable("id") int id) {
        OrderDetail order = orderService.getOrderById(id);
        int userId = TokenUtil.getUserId(request);
        if (userId != order.getUserId()) {
            throw new NotPermittedException();
        }
        return new Response().success(order);
    }
}
