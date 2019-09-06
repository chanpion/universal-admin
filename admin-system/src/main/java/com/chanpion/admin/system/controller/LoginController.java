package com.chanpion.admin.system.controller;

import com.chanpion.admin.system.utils.ShiroUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author April Chen
 * @date 2019/9/6 16:52
 */
@Controller
public class LoginController {
    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroUtil.isLogin()) {
            return "redirect:/";
        }
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model) {
        return "/login";
    }

    /**
     * 跳转后台控制台
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtil.logout();
        return "/login";
    }
}
