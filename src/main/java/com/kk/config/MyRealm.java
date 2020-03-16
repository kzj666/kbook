package com.kk.config;

/*
@author kzj
@date 2020/3/16 - 17:23
*/

import com.kk.entity.User;
import com.kk.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm{

    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    UserService userService;

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        logger.info("执行了认证--MyRealm.doGetAuthenticationInfo");
        //userToken：是一个简单的用户名/密码身份验证令牌，以支持使用最广泛的身份验证机制。
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //连接真实数据库进行认证
        User user = userService.queryByName(userToken.getUsername());

        if(user == null){
            return null;
        }

        //进行密码认证，由shiro来干
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");

    }

    /**
     * Authorization是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("执行了授权--MyRealm.doGetAuthenticationInfo()");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取当前登录的对象
        User currentUser = (User)principals.getPrimaryPrincipal();
        //设置当前用户的权限


    }



}
