package com.zhourb.familyaccount_api.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhourb.familyaccount_api.common.exception.TokenIsExpiredException;
import com.zhourb.familyaccount_api.module.Service.ServiceImpl.UserServiceImpl;
import com.zhourb.familyaccount_api.utils.http.HttpResult;
import com.zhourb.familyaccount_api.utils.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/14 13:40
 * @description
 **/
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
       
        // TODO Auto-generated method stub
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            HttpResult httpResult = HttpResult.error(HttpStatus.SC_UNAUTHORIZED,
//                    "authentication failed, reason: " + e.getMessage());
            HttpResult httpResult = HttpResult.error(HttpStatus.SC_UNAUTHORIZED,
                    ": " + e.getMessage());

            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(httpResult));
            out.flush();
            out.close();
            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws TokenIsExpiredException {
        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        boolean expiration = jwtTokenUtil.isExpiration(token);
        if (expiration) {
            throw new TokenIsExpiredException("令牌过期,请重新登录");
        } else {
            String username = jwtTokenUtil.getusername(token);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                return authentication;
            }
        }
        return null;
    }
}
