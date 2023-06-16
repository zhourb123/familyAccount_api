package com.zhourb.familyaccount_api.config.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/11 15:30
 * @description 用户登录成功触发 禁用
 **/

public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>  {
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        System.out.println("登录成功");
    }
}
