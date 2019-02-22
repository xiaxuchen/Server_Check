package com.cxyz.check.shiro.realm;

import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.exception.user.PasswordErrorException;
import com.cxyz.check.exception.user.UserNotFoundException;
import com.cxyz.check.service.UserService;
import com.cxyz.check.shiro.role.Roles;
import com.cxyz.check.shiro.token.UserToken;
import com.cxyz.check.typevalue.PowerType;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class UserRealm extends AuthorizingRealm {

    {
        setName("userRealm");
    }

    @Autowired
    private UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer power = (Integer) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRoles(power);
        if (roles == null || roles.isEmpty())
            return null;
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(getPermissionsByRoles(roles));
        return authorizationInfo;
    }

    /**
     * 获取权限
     * @param roles
     * @return
     */
    private Set<String> getPermissionsByRoles(Set<String> roles) {
        Set<String> permissions = new HashSet<>();
        return permissions;
    }

    /**
     * 获取角色
     * @param power
     * @return
     */
    private Set<String> getRoles(Integer power) {
        Set<String> roles = new HashSet<>();
        switch (power)
        {
            case PowerType.STU_CHECKER:{
                roles.add(Roles.USER);
                roles.add(Roles.STU);
                roles.add(Roles.CHECKER);
            }break;
            case PowerType.STU_NORMAL:{
                roles.add(Roles.USER);
                roles.add(Roles.STU);
            }break;
            case PowerType.TEA_NORMAL:{
                roles.add(Roles.USER);
                roles.add(Roles.TEACHER);
            }break;
            case PowerType.TEA_HEAD:{
                roles.add(Roles.USER);
                roles.add(Roles.TEACHER);
                roles.add(Roles.HEADTEACHER);
            }break;
            case PowerType.TEA_COLLEGE:{
                roles.add(Roles.USER);
                roles.add(Roles.TEACHER);
                roles.add(Roles.COLLEGETEACHER);
            }break;
            case PowerType.TEA_SCHOOL:{
                roles.add(Roles.USER);
                roles.add(Roles.TEACHER);
                roles.add(Roles.SCHOOLTEACHER);
            }break;
        }
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UserToken token = (UserToken) authenticationToken;
        LoginDto login;
        try {
            login = service.login(token.getUsername(), String.valueOf(token.getPassword()), token.getType());
        }catch (UserNotFoundException e)
        {
            throw new UnknownAccountException();
        }catch (PasswordErrorException e)
        {
            throw new IncorrectCredentialsException();
        }catch (Exception e)
        {
            throw new AuthenticationException("服务器内部异常");
        }
        token.setLoginDto(login);
        AuthenticationInfo info = new SimpleAuthenticationInfo(login.getPower(),token.getPassword(),getName());
        return info;
    }
}
