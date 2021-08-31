package com.security.auth.server.service.impl;

import com.security.auth.server.util.SM3Util;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.Collections;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/4/29 13:52
 * @since 1.0.0
 */

public class MyClientDetailsServiceImpl implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        /**
         * 模拟查询数据库获取数据
         */
        BaseClientDetails details = new BaseClientDetails();

        // 客户端ID
        details.setClientId(clientId);

        // 客户端访问密匙
        details.setClientSecret(SM3Util.encode("client_passwd_wxm"));

        // 客户端支持的授权许可类型(grant_type)，可选值包括authorization_code,password,refresh_token,implicit,client_credentials,若支持多个授权许可类型用逗号(,)分隔
        details.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "password", "refresh_token","check_token"));

        //客户端申请的权限范围，可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔
        details.setScope(Arrays.asList("read", "trust", "write"));

        // 客户端所能访问的资源id集合，多个资源时用逗号(,)分隔
        details.setResourceIds(Arrays.asList("oauth2-resource", "rid"));

        // 客户端重定向URI，当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与数据库内的redirect_uri是否一致
        details.setRegisteredRedirectUri(Collections.singleton("http://anywhere.com"));

        // 设定客户端的access_token的有效时间值(单位:秒)，若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)
        details.setAccessTokenValiditySeconds(60 * 60 * 4);

        // 设定客户端的refresh_token的有效时间值(单位:秒)，若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天)
        details.setRefreshTokenValiditySeconds(60 * 60 * 24);

        // 客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔
/*        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        details.setAuthorities(authorities);*/

        // 设置用户是否自动批准授予权限操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’.
        //details.setAutoApproveScopes(Arrays.asList("false"));

        return details;
    }
}
