package com.chanpion.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author April Chen
 * @date 2019/9/10 13:55
 */
@Controller
public class MainController {

    @RequestMapping("/error/{code}")
    public String error(@PathVariable String code) {
        return "error/" + code;
    }
}
