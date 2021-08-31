package com.security.auth.server.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/4/27 13:51
 * @since 1.0.0
 */
@Data
public class MyUsernamePasswordAuthenticationToken {
    private Object principal;
    private Object credentials;
    private Collection<MyGrantedAuthority> authorities;
    private Object details;
    private boolean authenticated = false;

    @Data
    class MyGrantedAuthority implements GrantedAuthority {
        private String authority;
    }
}
