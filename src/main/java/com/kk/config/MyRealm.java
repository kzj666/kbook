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

import java.util.HashSet;

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
        //获取用户输入的账号
        String username = userToken.getUsername();
        //连接真实数据库进行认证
        User user = userService.queryByName(username);
        //没有该用户则返回null，则会抛出loginController类下cache的异常
        if(user == null){
            return null;
        }
        //如果查询到由此用户，将认证信息返回，(如有定义加密方式，则会以自定义的加密方式的密码验证)
        //进行密码认证，由shiro来干,此处可采用加密方式
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());

    }

    /**
     * Authorization是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * 注解调用有hasRole,hasPermission的时候，或页面发现shiro标签，才会进行回调此授权方法.
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("执行了授权--MyRealm.doGetAuthenticationInfo()");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的对象
        User currentUser = (User)principals.getPrimaryPrincipal();

        //此处通过user.getRoles拿到数据库中user的用户角色

        //设置当前用户的权限

        HashSet<String> set = new HashSet<>();
        set.add("user:list");
        set.add("user:kk");
        HashSet<String> set2 = new HashSet<>();
        set2.add("user:list");
        set2.add("user:jj");

        //此处模拟 用户角色权限
        if("admin".equals(currentUser.getUsername())){
            info.addStringPermission("user:list");
            System.out.println("==============admin================");

        }else if("kk".equals(currentUser.getUsername())) {
            info.addStringPermissions(set);
            System.out.println("==============kk================");
        }else{
            info.addStringPermissions(set2);
            System.out.println("==============jj================");
        }

        return info;
    }
}
