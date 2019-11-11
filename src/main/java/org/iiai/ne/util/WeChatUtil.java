package org.iiai.ne.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.common.util.crypto.PKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.iiai.ne.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatUtil.class);

    public static JSONObject validateCode(String appId, String appSecret, String code) {
        RestTemplate template = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        ResponseEntity<String> responseEntity = template.getForEntity(ConstantUtil.VALIDATE_URI, String.class, params);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            LOGGER.error("Wechat server response {}", responseEntity.getStatusCode());
            throw new InternalException();
        }
        LOGGER.info("Wechat response {}", responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody());
    }

    public static JSONObject decryptWeChatData(String data, String sessionKey, String iv) {
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(Base64.decodeBase64(iv)));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), algorithmParameters);
            byte[] decode = PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(data)));
            String decryptStr = new String(decode, StandardCharsets.UTF_8);
            return JSON.parseObject(decryptStr);
        } catch (Exception e) {
            LOGGER.error("decrypt error", e);
            throw new InternalException();
        }


    }
}
