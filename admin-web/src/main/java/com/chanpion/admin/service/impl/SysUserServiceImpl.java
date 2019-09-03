package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.common.util.ShiroUtil;
import com.chanpion.admin.entity.SysUser;
import com.chanpion.admin.entity.SysUserRole;
import com.chanpion.admin.mapper.SysUserMapper;
import com.chanpion.admin.mapper.SysUserRoleMapper;
import com.chanpion.admin.service.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * SysUser 表数据服务层接口实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public void insertUser(SysUser user, String[] roleIds) {
        // TODO Auto-generated method stub
        user.setCreateTime(new Date());
        user.setPassword(ShiroUtil.md51024Pwd(user.getPassword(), user.getUserName()));
        //保存用户
        userMapper.insert(user);
        //绑定角色
        bindRoles(user, roleIds);

    }

    private void bindRoles(SysUser user, String[] roleIds) {
        if (ArrayUtils.isNotEmpty(roleIds)) {
            for (String rid : roleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getId());
                sysUserRole.setRoleId(rid);
                userRoleMapper.insert(sysUserRole);
            }
        }
    }

    @Override
    public void updateUser(SysUser sysUser, String[] roleIds) {
        // TODO Auto-generated method stub
        sysUser.setPassword(null);
        //更新用户
        userMapper.updateById(sysUser);
        //删除已有权限
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("userId", sysUser.getId()));
        //重新绑定角色
        bindRoles(sysUser, roleIds);
    }

    @Override
    public Page<Map<Object, Object>> selectUserPage(Page<Map<Object, Object>> page, String search) {
        // TODO Auto-generated method stub
        page.setRecords(baseMapper.selectUserList(page, search));
        return page;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        this.removeById(id);
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().inSql("userId = {0}", id));
    }


}