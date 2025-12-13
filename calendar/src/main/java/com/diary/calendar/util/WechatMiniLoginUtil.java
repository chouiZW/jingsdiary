package com.diary.calendar.util;
import com.diary.calendar.config.WechatMiniConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

@Component
public class WechatMiniLoginUtil {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WechatMiniLoginUtil.class);

    @Autowired
    private WechatMiniConfig wechatMiniConfig;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 微信小程序code换openid
     * @param code 小程序端调用wx.login()获取的code
     * @return openid 或 null（失败）
     */
    public String getOpenidByCode(String code) {
        if (!StringUtils.hasText(code)) {
            return null;
        }

        // 拼接微信接口请求参数
        String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatMiniConfig.getLoginUrl(),
                wechatMiniConfig.getAppid(),
                wechatMiniConfig.getAppsecret(),
                code);

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            JSONObject jsonObject = JSONObject.parseObject(responseBody);

            // 微信接口返回errcode则表示失败
            if (jsonObject.containsKey("errcode") && jsonObject.getInteger("errcode") != 0) {
               log.error("微信code换openid失败：{}", responseBody);
                return null;
            }

            // 返回openid
            return jsonObject.getString("openid");
        } catch (Exception e) {
           log.error("调用微信登录接口异常", e);
            return null;
        }
    }
}