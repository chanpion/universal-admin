package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.entity.SysUserRole;
import com.chanpion.admin.mapper.SysUserRoleMapper;
import com.chanpion.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SysUserRole 表数据服务层接口实现类
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public Set<String> findRolesByUid(String uid) {
        // TODO Auto-generated method stub
        List<SysUserRole> list = this.list(new QueryWrapper<SysUserRole>().eq("userId", uid));

        Set<String> set = new HashSet<>();
        for (SysUserRole ur : list) {
            set.add(ur.getRoleId());
        }
        return set;
    }
}