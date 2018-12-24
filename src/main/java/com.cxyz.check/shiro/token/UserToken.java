package com.cxyz.check.shiro.token;

import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {

    //用户类型
    private Integer type;

    //获取到的用户信息
    private LoginDto loginDto;

    public UserToken(String username, char[] password, Integer type) {
        super(username, password);
        this.type = type;
    }

    public UserToken(String username, String password, Integer type) {
        super(username, password);
        this.type = type;
    }

    public LoginDto getLoginDto() {
        return loginDto;
    }

    public void setLoginDto(LoginDto loginDto) {
        this.loginDto = loginDto;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public void clear() {
        super.clear();
        type = null;
    }

    @Override
    public String toString() {
        return super.toString()+"\nUserToken{" +
                "type=" + type +
                ", user=" + loginDto +
                '}';
    }
}
