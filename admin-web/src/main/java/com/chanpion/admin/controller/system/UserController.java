package com.chanpion.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanpion.admin.common.anno.Log;
import com.chanpion.admin.common.bean.Rest;
import com.chanpion.admin.common.controller.SuperController;
import com.chanpion.admin.entity.SysRole;
import com.chanpion.admin.entity.SysUser;
import com.chanpion.admin.entity.SysUserRole;
import com.chanpion.admin.service.ISysDeptService;
import com.chanpion.admin.service.ISysRoleService;
import com.chanpion.admin.service.ISysUserRoleService;
import com.chanpion.admin.service.SysUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * @author Gaojun.Zhou
 * @date 2016年12月13日 上午10:22:41
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends SuperController {

	@Autowired
    private SysUserService sysUserService;
	@Autowired
    private ISysRoleService sysRoleService;
	@Autowired
    private ISysUserRoleService sysUserRoleService;
	@Autowired
    private ISysDeptService sysDeptService;
	
	/**
	 * 分页查询用户
	 */
	@RequiresPermissions("listUser")
    @RequestMapping("/list/{pageNumber}")
    public  String list(@PathVariable Integer pageNumber, @RequestParam(defaultValue="15") Integer pageSize, String search, Model model){
		if(StringUtils.isNotBlank(search)){
			model.addAttribute("search", search);
		}
    	Page<Map<Object, Object>> page = getPage(pageNumber,pageSize);
    	model.addAttribute("pageSize", pageSize);
    	Page<Map<Object, Object>> pageData = sysUserService.selectUserPage(page, search);
    	model.addAttribute("pageData", pageData);
    	return "system/user/list";
    } 
    /**
     * 新增用户
     */
	@RequiresPermissions("addUser")
    @RequestMapping("/add")
    public  String add(Model model){
    	model.addAttribute("roleList", sysRoleService.list(null));
    	model.addAttribute("deptList", sysDeptService.list(null));
		return "system/user/add";
    } 
    
    /**
     * 执行新增
     */
    @Log("创建用户")
    @RequiresPermissions("addUser")
    @RequestMapping("/doAdd")
    @ResponseBody
    public Rest doAdd(SysUser user, @RequestParam(value="roleId[]",required=false) String[] roleId){
    	
    	sysUserService.insertUser(user,roleId);
    	return Rest.ok();
    }  
    /**
     * 删除用户
     */
    @Log("删除用户")
    @RequiresPermissions("deleteUser")
    @RequestMapping("/delete")
    @ResponseBody
    public  Rest delete(String id){
    	sysUserService.delete(id);
    	return Rest.ok();
    }  
    
	/**
	 * 编辑用户
	 */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("editUser")
    public  String edit(@PathVariable String id, Model model){
    	SysUser sysUser = sysUserService.getById(id);
    	
    	List<SysRole> sysRoles = sysRoleService.list(null);
    	QueryWrapper<SysUserRole> ew = new QueryWrapper<SysUserRole>();
    	ew.eq("userId ", id);
    	List<SysUserRole> mySysUserRoles = sysUserRoleService.list(ew);
    	List<String> myRolds = Lists.transform(mySysUserRoles, input -> input.getRoleId());
    	
    	model.addAttribute("sysUser",sysUser);
    	model.addAttribute("sysRoles",sysRoles);
    	model.addAttribute("myRolds",myRolds);
    	model.addAttribute("deptList", sysDeptService.list(null));
    	return "system/user/edit";
    } 
    /**
     * 执行编辑
     */
    @Log("编辑用户")
    @RequiresPermissions("editUser")
    @RequestMapping("/doEdit")
    @ResponseBody
    public  Rest doEdit(SysUser sysUser, @RequestParam(value="roleId[]",required=false) String[] roleId, Model model){
    	sysUserService.updateUser(sysUser,roleId);
    	return Rest.ok();
    } 
    
    /**
     * 验证用户名是否已存在
     */
    @RequestMapping("/checkName")
    @ResponseBody
    public Rest checkName(String userName){
    	List<SysUser> list = sysUserService.list(new QueryWrapper<SysUser>().eq("userName", userName));
    	if(list.size() > 0){
    		return Rest.failure("用户名已存在");
    	}
    	return Rest.ok();
    }
    
}
