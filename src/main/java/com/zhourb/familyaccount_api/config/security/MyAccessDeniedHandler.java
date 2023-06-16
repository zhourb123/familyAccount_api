package com.zhourb.familyaccount_api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhourb.familyaccount_api.utils.http.HttpResult;
import com.zhourb.familyaccount_api.utils.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/14 11:44
 * @description 无权限时处理
 **/
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        HttpResult httpResult = HttpResult.error(HttpStatus.SC_FORBIDDEN,
//                "authentication failed reason:权限不足, " + accessDeniedException.getMessage());
        HttpResult httpResult = HttpResult.error(HttpStatus.SC_FORBIDDEN,
                ":权限不足, " + accessDeniedException.getMessage());

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(httpResult));
        out.flush();
        out.close();
    }
}
