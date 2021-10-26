package com.xuan.controller;

import com.xuan.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;//返回到url界面，需要在配置文件配视图解析器
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            Account account = (Account) subject.getPrincipal(); //登陆成功，用户信息的是已经存到了subject中，现在只需要取出
            subject.getSession().setAttribute("account", account);
            return "index";
        } catch (UnknownAccountException e) {
//第一个捕获用户名不存在异常
            e.printStackTrace();
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
//非法密码异常
            e.printStackTrace();
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//相当于销毁session
        return "login";//返回到登陆界面


    }


}
