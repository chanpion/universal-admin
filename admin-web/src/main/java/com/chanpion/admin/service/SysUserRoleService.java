package com.chanpion.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chanpion.admin.entity.SysUserRole;

import java.util.Set;

/**
 * SysUserRole 表数据服务层接口
 *
 * @author April Chen
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户的角色
     *
     * @param uid 用户id
     * @return 角色列表
     */
    Set<String> findRolesByUid(String uid);
}