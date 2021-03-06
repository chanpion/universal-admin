package com.chanpion.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanpion.admin.entity.SysUserRole;

import java.util.List;

/**
 * SysUserRole 表数据库控制层接口
 * @author April Chen
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    List<String> selectPermissionByUid(String uid);
}