package org.iiai.ne.controller;

import org.apache.commons.lang3.StringUtils;
import org.iiai.ne.aop.Privilege;
import org.iiai.ne.exception.BadRequestException;
import org.iiai.ne.model.Address;
import org.iiai.ne.model.Company;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.AddressService;
import org.iiai.ne.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.PUT)
    @Privilege
    public Response updateAddr(HttpServletRequest request, @RequestBody Address address) {
        int userId = TokenUtil.getUserId(request);
        isValidAddr(address);
        addressService.updateAddress(userId, address);
        return new Response().success();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @Privilege
    public Response deleteAddr(HttpServletRequest request, @PathVariable("id") int id) {
        int userId = TokenUtil.getUserId(request);
        addressService.delDelAddrById(userId, id);
        return new Response().success();
    }

    @RequestMapping(method = RequestMethod.POST)
    @Privilege
    public Response insertDelAddr(HttpServletRequest request, @RequestBody Address address) {
        int userId = TokenUtil.getUserId(request);
        isValidAddr(address);
        addressService.insertAddress(userId, address);
        return new Response().success();
    }

    private void isValidAddr(Address address) {
        String userName = address.getUserName();
        if (!(!StringUtils.isEmpty(userName) && userName.length() < 33)) {
            LOGGER.error("userName is invalid");
            throw new BadRequestException();
        }
        String phone = address.getPhone();
        if (!Pattern.matches("^05\\d{8}$", phone)) {
            LOGGER.error("mobile number is invalid");
            throw new BadRequestException();
        }
        Company company = address.getCompany();
        if (!(company != null && company.getId() > 0)) {
            LOGGER.error("Company is invalid");
            throw new BadRequestException();
        }

    }

}
