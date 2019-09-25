package com.chanpion.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author April Chen
 * @date 2019/9/25 20:24
 */
@Controller
@RequestMapping("/")
public class RoleController {

    @RequestMapping
    public String role(Model model) {
        return "role";
    }
}
