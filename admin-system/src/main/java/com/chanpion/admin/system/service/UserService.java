package com.chanpion.admin.system.service;

import com.chanpion.admin.system.dao.UserDao;
import com.chanpion.admin.system.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author April Chen
 * @date 2019/9/6 16:36
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public User findByUsername(String username) {
        return null;
    }

}