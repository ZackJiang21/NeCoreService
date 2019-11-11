package org.iiai.ne.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.iiai.ne.exception.BadRequestException;
import org.iiai.ne.exception.InternalException;
import org.iiai.ne.exception.NotPermittedException;
import org.iiai.ne.exception.UnAuthorizedException;
import org.iiai.ne.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class TokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);

    private static String key;

    static {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            key = new String(secretKey.getEncoded(), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("init key error", e);
        }
    }

    public static String getJwtToken(User user) {
        try {
            Algorithm al = Algorithm.HMAC256(key);
            String token = JWT.create()
                    .withIssuer("ne")
                    .withSubject("ne-token")
                    .withClaim("userId", user.getId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                    .sign(al);
            return token;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("No such algorithm");
            throw new InternalException();
        }
    }

    public static DecodedJWT verifyJwt(String token) {
        try {
            if (token == null) {
                throw new NotPermittedException();
            }
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (Exception e) {
            LOGGER.error("auth fail.", e);
            throw new UnAuthorizedException();
        }
    }

    public static int getUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader(ConstantUtil.TOKEN_USER_ID);
        if (StringUtils.isEmpty(userIdStr)) {
            throw new BadRequestException();
        }
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        }
        return userId;
    }
}
