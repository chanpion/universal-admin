package com.chanpion.admin.system.controller;

import com.chanpion.admin.system.entity.User;
import com.chanpion.admin.system.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April Chen
 * @date 2019/9/24 14:14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping
    public String user(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pageData", new PageInfo<>(users));
        return "system/user";
    }


}
