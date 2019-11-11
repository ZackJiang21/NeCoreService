package org.iiai.ne.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.iiai.ne.dao.UserDao;
import org.iiai.ne.exception.BadRequestException;
import org.iiai.ne.exception.HttpNotFoundException;
import org.iiai.ne.model.LogInData;
import org.iiai.ne.model.User;
import org.iiai.ne.util.TokenUtil;
import org.iiai.ne.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Value("${application.wechat.appid}")
    private String appid;

    @Value("${application.wechat.secret}")
    private String appSecret;

    @Autowired
    private UserDao userDao;

    @Override
    public String createSession(LogInData logInData) {
        JSONObject validateResult = WeChatUtil.validateCode(appid, appSecret, logInData.getCode());
        String openId = validateResult.getString("openid");
        if (StringUtils.isEmpty(openId)) {
            throw new BadRequestException("OpenId is empty");
        }
        User user = userDao.getUser(openId, 0);
        if (null == user) {
            LOGGER.info("user not exist in database, creating the user");
            JSONObject userObject = WeChatUtil.decryptWeChatData(logInData.getData(), validateResult.getString("session_key"), logInData.getIv());
            user = new User(openId, userObject.getString("nickName"), userObject.getString("avatarUrl"));
            userDao.insertUser(user);
        }
        return TokenUtil.getJwtToken(user);
    }

    @Override
    public String refreshSession(int userId) {
        User user = userDao.getUser(null, userId);
        if (null == user) {
            throw new HttpNotFoundException();
        }
        return TokenUtil.getJwtToken(user);
    }
}
