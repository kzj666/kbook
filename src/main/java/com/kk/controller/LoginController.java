package com.kk.controller;

/*
@author kzj
@date 2020/3/15 - 14:58
*/

import com.kk.entity.User;
import com.kk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author asus
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login_in")
    public String tologin(User user, HttpServletRequest request, Model model){

        User user1 = userService.queryByNamePass(user.getUsername(), user.getPassword());
        if(user1 == null){
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user1);
        return "redirect:/user/selectList";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }


}
