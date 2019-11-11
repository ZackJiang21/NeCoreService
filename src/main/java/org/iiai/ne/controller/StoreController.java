package org.iiai.ne.controller;

import org.iiai.ne.aop.Privilege;
import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.Response;
import org.iiai.ne.model.StoreDetail;
import org.iiai.ne.service.StoreService;
import org.iiai.ne.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response getStoreInfoById(@PathVariable("id") int id) {
        StoreDetail storeInfo = storeService.getStoreInfoById(id);
        return new Response().success(storeInfo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/coupon")
    @Privilege
    public Response getCouponInStore(HttpServletRequest request, @PathVariable("id") int id) {
        int userId = TokenUtil.getUserId(request);
        Coupon coupon = storeService.getCouponInStore(userId, id);
        return new Response().success(coupon);
    }
}
