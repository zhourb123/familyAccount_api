package com.zhourb.familyaccount_api.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/11 12:46
 * @description 密码加密
 **/
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
