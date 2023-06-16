package com.zhourb.familyaccount_api.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/11 14:21
 * @description jwt生成工具类
 **/

public class JwtTokenUtil {
    public static  String TOKEN_HEADER = "Authorization";
    public static  String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "zhourb";
    private static final  String ISS = "familyAccount";
    private static final String ROLE_CLAIMS= "claims";
    private static final long EXPIRATION = 36000000;

    /**
    *  @description： createToken 创建jwt
    *  @param:  用户名
    *  @return: string
    *  @date:2022/2/15 10:04
    **/
    public  String createToken(String username) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET).claim(ROLE_CLAIMS, username).setIssuer(ISS)
                .setSubject(username).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000)).compact();
    }
    public  String getusername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
    *  @description： isExpiration jwt是否过期
    *  @param: string jwt
    *  @return: boolean
    *  @date:2022/2/15 9:53
    **/
    public  boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
    private  Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

}
