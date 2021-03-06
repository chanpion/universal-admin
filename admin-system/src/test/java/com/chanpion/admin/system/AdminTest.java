package com.chanpion.admin.system;

import com.chanpion.admin.system.dao.UserDao;
import com.chanpion.admin.system.entity.User;
import com.chanpion.admin.system.utils.ShiroUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April Chen
 * @date 2019/9/6 15:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {

    @Resource
    private UserDao userDao;

    @Test
    public void testUser() {
        List<User> users = userDao.selectAll();
        users.forEach(System.out::println);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        String encyptPassword = ShiroUtil.encryptPassword(user);
        user.setPassword(encyptPassword);
        user.setMobile("18200000000");
        userDao.save(user);
    }
}
