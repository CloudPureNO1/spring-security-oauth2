package com.security.resources.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/6/4 11:21
 * @since 1.0.0
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthrizetionResourcesConfig extends ResourceServerConfigurerAdapter {
    private final RedisConnectionFactory redisConnectionFactory;
    public AuthrizetionResourcesConfig(RedisConnectionFactory redisConnectionFactory){
        this.redisConnectionFactory = redisConnectionFactory;
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
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("rid");
        resources.tokenStore(redisTokenStore(redisConnectionFactory));
    }

    /**
     * 这里设置需要token验证的url
     * 可以在WebSecurityConfigurerAdapter中排除掉，
     * 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行
     * WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /**
                 * 对资源的权限判断应该动态的放在元数据中判断，
                 * 对于方法访问权限  @EnableGlobalMethodSecurity(prePostEnabled = true)  // 可以进行方法校验  校验资源权限
                 * 配合  @PreAuthorize("hasAuthority('admin')")   使用
                 */
                /*              .antMatchers("/admin/**").hasRole("admin")
                              .antMatchers("/user/**").hasRole("user")*/
                // 设置 /login 无需权限访问
                .antMatchers("/login").permitAll()
                // 设置其它请求，需要认证后访问
                .anyRequest().authenticated();
    }
}
