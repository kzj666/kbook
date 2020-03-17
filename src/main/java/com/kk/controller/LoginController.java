package com.kk.controller;

/*
@author kzj
@date 2020/3/15 - 14:58
*/

import com.kk.entity.User;
import com.kk.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @author asus
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping({"/login","/"})
    public String tologin(){
        return "login";
    }

    @PostMapping("/login_in")
    public String login(User user, Model model){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            //login认证通过后，便可拿到shiro保存的用户对象
            User user1 = (User)subject.getPrincipal();
            subject.getSession().setAttribute("user",user1);
            return "redirect:/user/selectList";
        }catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @GetMapping("/noauth")
    public String noauth(){
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }


}
