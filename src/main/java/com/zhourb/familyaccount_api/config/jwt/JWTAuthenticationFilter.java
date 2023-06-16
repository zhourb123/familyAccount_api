package com.zhourb.familyaccount_api.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhourb.familyaccount_api.module.Entity.User;
import com.zhourb.familyaccount_api.module.Vo.UserVo;
import com.zhourb.familyaccount_api.utils.http.HttpResult;
import com.zhourb.familyaccount_api.utils.http.HttpStatus;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/14 13:41
 * @description
 **/
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        try {
            User user = (User) new ObjectMapper().readValue(request.getInputStream(), User.class);
            String userCode = user.getCode().toLowerCase();
            if (StringUtils.isEmpty(userCode)) {
                HttpResult httpResult = HttpResult.error(HttpStatus.SC_BAD_REQUEST,
                        "authentication failed, reason: 验证码不能为空");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(httpResult));
                out.flush();
                out.close();
                return null;
            } else {
                if (((request.getSession().getAttribute("VerifyCode")) == null)) {
                    HttpResult httpResult = HttpResult.error(HttpStatus.SC_BAD_REQUEST,
                            "authentication failed, reason: 请先获取验证码");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(httpResult));
                    out.flush();
                    out.close();
                    return null;
                } else {
                    String verifyCode = ((String) request.getSession().getAttribute("VerifyCode")).toLowerCase();
                    if (userCode.equals(verifyCode)) {
                        return authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                    } else {
                        HttpResult httpResult = HttpResult.error(HttpStatus.SC_BAD_REQUEST,
                                "authentication failed, reason: 验证码错误");
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(httpResult));
                        out.flush();
                        out.close();
                        return null;
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        // TODO Auto-generated method stub
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

//        System.out.println(authorities);


        User user = (User) authResult.getPrincipal();
        UserVo userVo = new UserVo();
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.createToken(authResult.getName());
        userVo.setUsername(user.getUsername());
        userVo.setUsernick(user.getUsernick());
        userVo.setToken(token);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(HttpResult.ok(userVo)));
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        String message = "";
        if (failed instanceof BadCredentialsException) {
            message = "账号或密码错误!";
        } else if (failed instanceof LockedException) {
            message = "账户被锁定!";
        } else if (failed instanceof CredentialsExpiredException) {
            message = "密码过期，请修改密码!";
        } else if (failed instanceof AccountExpiredException) {
            message = "账号过期，请联系管理员";
        } else if (failed instanceof DisabledException) {
            message = "账号被禁用，请联系管理员!";
        } else {
            message = "未知登录错误，请联系管理员!";
        }
//
        HttpResult httpResult = HttpResult.error(HttpStatus.SC_BAD_REQUEST,
                " " + message);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(httpResult));
        out.flush();
        out.close();
    }
}
