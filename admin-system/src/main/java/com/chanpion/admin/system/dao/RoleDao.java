package com.chanpion.admin.system.dao;

import com.chanpion.admin.system.entity.Role;

import java.util.List;

/**
 * @author April Chen
 * @date 2019/9/25 13:53
 */
public interface RoleDao {

    /**
     * 新增用户
     *
     * @param role
     * @return
     */
    int save(Role role);

    /**
     * 获取全部
     *
     * @return
     */
    List<Role> findAll();

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<Role> find(Long userId);
}
