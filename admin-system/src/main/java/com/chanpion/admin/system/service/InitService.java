package com.chanpion.admin.system.service;

import com.chanpion.admin.system.dao.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author April Chen
 * @date 2019/9/6 16:06
 */
@Service
public class InitService implements CommandLineRunner {
    @Resource
    private UserDao userDao;

    @Override
    public void run(String... args) throws Exception {
        userDao.selectAll().forEach(System.out::println);
    }
}
