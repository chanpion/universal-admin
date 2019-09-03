package com.chanpion.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chanpion.admin.entity.SysUser;

import java.util.Map;

/**
 * SysUser 表数据服务层接口
 *
 * @author April Chen
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户
     *
     * @param page   分页
     * @param search 查询
     * @return
     */
    Page<Map<Object, Object>> selectUserPage(Page<Map<Object, Object>> page, String search);

    /**
     * 保存用户
     *
     * @param user   用户
     * @param roleId 角色列表
     */
    void insertUser(SysUser user, String[] roleId);

    /**
     * 更新用户
     *
     * @param user   用户
     * @param roleId 角色列表
     */
    void updateUser(SysUser user, String[] roleId);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void delete(String id);

}