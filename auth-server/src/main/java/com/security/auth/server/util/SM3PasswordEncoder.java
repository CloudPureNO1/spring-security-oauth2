package com.security.auth.server.util;


import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>自定义加密类</p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/4/25 14:33
 * @since 1.0.0
 */
public class SM3PasswordEncoder implements PasswordEncoder {
    @SneakyThrows
    @Override
    public String encode(CharSequence charSequence) {
        return SM3Util.encode(charSequence.toString());
    }

    @SneakyThrows
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(SM3Util.encode(charSequence.toString()));
    }
}
