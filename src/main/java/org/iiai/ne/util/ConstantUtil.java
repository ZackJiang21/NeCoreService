package org.iiai.ne.util;

public interface ConstantUtil {
    String VALIDATE_URI = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}";

    String COOKIE_SESSION = "session";

    String TOKEN_USER_ID = "user-id";

    interface CouponStatus {
        int IS_NOT_CLAIMED = 0;

        int IS_CLAIMED = 1;

        int IS_USED = 2;
    }

    interface ActivityType {
        int GIFT = 1;
    }
}
