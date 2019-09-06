package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.entity.SysRoleMenu;
import com.chanpion.admin.mapper.SysMenuMapper;
import com.chanpion.admin.mapper.SysRoleMenuMapper;
import com.chanpion.admin.service.ISysRoleMenuService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SysRoleMenu 表数据服务层接口实现类
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public void addAuth(String roleId, String[] menuIds) {
        // TODO Auto-generated method stub

        /*
          删除原有权限
         */
        this.remove(new QueryWrapper<SysRoleMenu>().eq("roleId", roleId));
        /*
          重新授权
         */
        if (ArrayUtils.isNotEmpty(menuIds)) {
            for (String menuId : menuIds) {
                SysRoleMenu sysRoleMenu2 = new SysRoleMenu();
                sysRoleMenu2.setRoleId(roleId);
                sysRoleMenu2.setMenuId(menuId);
                this.save(sysRoleMenu2);
            }
        }
    }

    @Override
    public List<SysRoleMenu> selectByRole(String roleId) {
        // TODO Auto-generated method stub

        QueryWrapper<SysRoleMenu> ew = new QueryWrapper<>();
        ew.eq("roleId", roleId);
        return this.list(ew);

    }

    @Override
    public Set<String> findMenusByUid(String id) {
        // TODO Auto-generated method stub
        List<String> list = sysMenuMapper.selectResourceByUid(id);
        return new HashSet<>(list);
    }


}