package org.iiai.ne.controller;

import org.iiai.ne.aop.Privilege;
import org.iiai.ne.exception.NotPermittedException;
import org.iiai.ne.model.Address;
import org.iiai.ne.model.Coupon;
import org.iiai.ne.model.PaginationData;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.AddressService;
import org.iiai.ne.service.CouponService;
import org.iiai.ne.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/address")
    @Privilege
    public Response getAddressByUserId(HttpServletRequest request, @PathVariable("userId") int userId) {
        validateUserId(request, userId);
        List<Address> addresses = addressService.getAddressByUserId(userId);
        return new Response().success(addresses);
    }

    private void validateUserId(HttpServletRequest request, int userId) {
        int tokenUserId = TokenUtil.getUserId(request);
        if (tokenUserId != userId) {
            LOGGER.error("user {} request userId {}", tokenUserId, userId);
            throw new NotPermittedException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/coupon")
    @Privilege
    public Response getCouponByUserId(HttpServletRequest request, @PathVariable("userId") int userId,
                                      @RequestParam(required = false, defaultValue = "0") int storeId,
                                      @RequestParam int pageSize, @RequestParam int sinceId) {
        validateUserId(request, userId);
        PaginationData<Coupon> couponPaginationData = couponService.getCouponByUserId(userId, storeId, pageSize, sinceId);
        return new Response().success(couponPaginationData);
    }

}
