package com.security.auth.server.config;

import com.security.auth.server.service.impl.MyClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p></p>
 * <p>
 *   ① 在类上添加 @EnableAuthorizationServer 注解，声明开启 OAuth 授权服务器的功能。
 *
 * 同时，继承 AuthorizationServerConfigurerAdapter 类，进行 OAuth 授权服务器的配置。
 *
 * ② #configure(AuthorizationServerEndpointsConfigurer endpoints) 方法，配置使用的 AuthenticationManager 实现用户认证的功能。其中，authenticationManager 是由「2.1.2 SecurityConfig」创建，Spring Security 的配置类。
 *
 * ③ #configure(AuthorizationServerSecurityConfigurer oauthServer) 方法，设置 /oauth/check_token 端点，通过认证后可访问。
 *
 * 友情提示：这里的认证，指的是使用 client-id + client-secret 进行的客户端认证，不要和用户认证混淆。
 *
 * 其中，/oauth/check_token 端点对应 CheckTokenEndpoint 类，用于校验访问令牌的有效性。
 *
 * 在客户端访问资源服务器时，会在请求中带上访问令牌。
 * 在资源服务器收到客户端的请求时，会使用请求中的访问令牌，找授权服务器确认该访问令牌的有效性。
 *
 * </p>
 *
 * @author 王森明
 * @date 2021/6/4 10:12
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final TokenEnhancer tokenEnhancer;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, RedisConnectionFactory redisConnectionFactory, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, @Qualifier("mytTokenEnhancer") TokenEnhancer tokenEnhancer) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenEnhancer = tokenEnhancer;
    }

    /**
     * 授权服务器和资源服务器必须一直，否则资料服务器调用的时候，回包token错误，（因为从不同存储的token导致）
     * token  采用Redis 存储
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许通过form提交客户端认证信息(client_id,client_secret),默认为basic方式认证
        security.allowFormAuthenticationForClients();
        /**
         * security.checkTokenAccess("isAuthenticated()");    设置 "/oauth/check_token"端点默认不允许访问
         * security.tokenKeyAccess("isAuthenticated()");       设置 "/oauth/token_key"断点默认不允许访问
         */
        security.checkTokenAccess("isAuthenticated()");
        // 配置密码编码器
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 注册自定义客户端信息服务
        clients.withClientDetails(new MyClientDetailsServiceImpl());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 集成SpringSecurity认证  开启密码授权类型
        endpoints.authenticationManager(authenticationManager)
                // 注册redis令牌仓库
                .tokenStore(redisTokenStore(redisConnectionFactory))
                // 自定义用户实现
                .userDetailsService(userDetailsService)
                // 自定义token实现
                .tokenEnhancer(tokenEnhancer);
    }

}
