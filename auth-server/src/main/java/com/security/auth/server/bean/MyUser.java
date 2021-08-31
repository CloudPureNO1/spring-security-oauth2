package com.security.auth.server.bean;

import lombok.Data;

/**
 * <p>用作测试时的数据，正式应该是取数据库中数据</p>
 *
 * @author 王森明
 * @date 2021/4/25 14:15
 * @since 1.0.0
 */
@Data
public class MyUser implements java.io.Serializable {
    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

}