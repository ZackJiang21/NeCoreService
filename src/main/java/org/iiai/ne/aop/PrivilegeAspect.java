package org.iiai.ne.aop;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.iiai.ne.common.MutableHttpServletRequest;
import org.iiai.ne.util.ConstantUtil;
import org.iiai.ne.util.TokenUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PrivilegeAspect {
    @Around("@annotation(org.iiai.ne.aop.Privilege) && args(request, ..)")
    public Object auth(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ConstantUtil.COOKIE_SESSION.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        DecodedJWT jwt = TokenUtil.verifyJwt(token);
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        mutableRequest.putHeader(ConstantUtil.TOKEN_USER_ID, jwt.getClaim("userId").as(String.class));

        Object[] args = joinPoint.getArgs();
        args[0] = mutableRequest;

        return joinPoint.proceed(args);
    }
}
