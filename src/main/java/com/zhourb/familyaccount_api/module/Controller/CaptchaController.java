package com.zhourb.familyaccount_api.module.Controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 周如彬
 * @description 获取验证码
 */
@Slf4j
@RestController
public class CaptchaController {
    @GetMapping("/getCaptcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(250, 100, 4, 4);
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        //获取验证码的值
        String code = captcha.getCode();
        log.info("图形验证码："+code);
        request.getSession().setAttribute("VerifyCode", code);
        captcha.write(response.getOutputStream());
    }
}
