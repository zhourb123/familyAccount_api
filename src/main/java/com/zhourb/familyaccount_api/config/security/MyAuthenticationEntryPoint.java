package com.zhourb.familyaccount_api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhourb.familyaccount_api.utils.http.HttpResult;
import com.zhourb.familyaccount_api.utils.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/11 16:06
 * @description
 **/
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        HttpResult httpResult = HttpResult.error(HttpStatus.SC_UNAUTHORIZED,
//                "authentication failed, reason: " + authException.getMessage());
        HttpResult httpResult = HttpResult.error(HttpStatus.SC_UNAUTHORIZED,
                ": " + authException.getMessage());

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(httpResult));
        out.flush();
        out.close();
    }
}
