package org.iiai.ne.controller;

import org.iiai.ne.aop.Privilege;
import org.iiai.ne.model.LogInData;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.SessionService;
import org.iiai.ne.util.ConstantUtil;
import org.iiai.ne.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.POST)
    public Response auth(@RequestBody LogInData logInData) {
        String token = sessionService.createSession(logInData);
        return new Response().success(token);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Privilege
    public Response refreshSession(HttpServletRequest request) {
        int userId = TokenUtil.getUserId(request);
        String token = sessionService.refreshSession(userId);
        return new Response().success(token);

    }

}
