package com.chanpion.admin.system.dao;

import com.chanpion.admin.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author April Chen
 * @date 2019/9/6 14:00
 */
public interface UserDao {
    /**
     * 新增用户
     * @param user
     * @return
     */
    int save (User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int update (User user);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById (int id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User selectById (int id);

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectAll ();
}
