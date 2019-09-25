package com.chanpion.admin.system.controller;

import com.chanpion.admin.system.utils.CaptchaUtil;
import com.chanpion.admin.system.utils.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author April Chen
 * @date 2019/9/6 16:52
 */
@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
//        if (ShiroUtil.isLogin()) {
//            return "redirect:/";
//        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Boolean rememberme, Model model) {
        if (rememberme == null) {
            rememberme = Boolean.FALSE;
        }
//        ensureLogout();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberme);
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                logger.info("user {} login success.", username);
                return "/index";
            } else {
                return "error";
            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误!";
            model.addAttribute("message", "密码有误，请重新输入!");
            logger.info(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多!";
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定! ";
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用! ";
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期! ";
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在!";
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
            logger.info(msg);
        } catch (Exception e) {
            logger.error("{}", e);
        } finally {
            model.addAttribute("message", msg);
        }
        return "/error";
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
        return "login";
    }

    /**
     * 验证码
     */
    @RequestMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaUtil.captcha(request, response);
    }

    /**
     * 确保完全注销
     */
    private void ensureLogout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser == null) {
                return;
            }
            currentUser.logout();
            Session session = currentUser.getSession(false);
            if (session == null) {
                return;
            }
            session.stop();
        } catch (Exception ignore) {
        }
    }
}
