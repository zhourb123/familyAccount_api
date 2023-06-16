package com.zhourb.familyaccount_api.module.Vo;

import java.io.Serializable;
import java.util.Set;

/**
 * @author 周如彬
 */
public class UserVo implements Serializable {
    private String username;
    private String usernick;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernick() {
        return usernick;
    }

    public void setUsernick(String usernick) {
        this.usernick = usernick;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
