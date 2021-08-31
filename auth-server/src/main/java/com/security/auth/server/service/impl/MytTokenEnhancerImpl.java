package com.security.auth.server.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>可以通过这个方法跳转token信息，也可以在controller 中重新写一个/oauth/token 接口，替代默认的</p>
 * <p>我们在请求token后，前端如果有需求，比如说要将用户信息显示在页面上，那么请求token的时候能不能给它添加一些额外参数呢？答案是肯定的，也比较简单，只需要实现TokenEnhancer接口就可以了，</p>
 *
 * @author 王森明
 * @date 2021/4/29 17:21
 * @since 1.0.0
 */
@Service("mytTokenEnhancer")
public class MytTokenEnhancerImpl implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserDetails account = (UserDetails) authentication.getPrincipal();
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> map = new LinkedHashMap<>();
        // 设置值
        map.put("acount", account);
        token.setAdditionalInformation(map);
        return token;
    }


}
