package com.chanpion.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chanpion.admin.common.anno.Log;
import com.chanpion.admin.common.bean.Rest;
import com.chanpion.admin.common.controller.SuperController;
import com.chanpion.admin.entity.SysMenu;
import com.chanpion.admin.service.ISysMenuService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 *
 * @author Gaojun.Zhou
 * @date 2016年12月13日 上午10:23:41
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends SuperController {

    /**
     * 菜单服务
     */
    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 分页查询菜单
     */
    @RequiresPermissions("listMenu")
    @RequestMapping("/list/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize, String search, Model model) {

        Page<SysMenu> page = getPage(pageNumber, pageSize);
        page.addOrder(OrderItem.asc("code"));
        model.addAttribute("pageSize", pageSize);
        // 查询分页
        QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
        if (StringUtils.isNotBlank(search)) {
            ew.like("menuName", search);
            model.addAttribute("search", search);
        }
        IPage<SysMenu> pageData = sysMenuService.page(page, ew);

        for (SysMenu menu : pageData.getRecords()) {
            if (menu.getPid() == null || menu.getDeep() != 3) {
                menu.setMenuName(StringUtils.join("<i class='fa fa-folder-open'></i> ", menu.getMenuName()));
            } else {
                menu.setMenuName(StringUtils.join("<i class='fa fa-file'></i> ", menu.getMenuName()));
            }
            for (int i = 1; i < menu.getDeep(); i++) {
                menu.setMenuName(StringUtils.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", menu.getMenuName()));
            }

        }

        model.addAttribute("pageData", pageData);
        return "system/menu/list";
    }

    /**
     * 增加菜单
     */
    @RequiresPermissions("addMenu")
    @RequestMapping("/add")
    public String add(Model model) {

        QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
        ew.orderBy("code", true);
        ew.eq("pid", "0");
        List<SysMenu> list = sysMenuService.list(ew);
        model.addAttribute("list", list);
        return "system/menu/add";

    }

    /**
     * 添加目录
     */
    @RequiresPermissions("addMenu")
    @Log("创建目录菜单")
    @RequestMapping("/doAddDir")
    @ResponseBody
    public Rest doAddDir(SysMenu sysMenu, Model model) {

        sysMenu.setPid("0");
        sysMenu.setDeep(1);
        sysMenuService.save(sysMenu);
        return Rest.ok();
    }

    /**
     * 添加菜单
     */
    @RequiresPermissions("addMenu")
    @Log("创建菜单")
    @RequestMapping("/doAddMenu")
    @ResponseBody
    public Rest doAddMenu(SysMenu sysMenu, Model model) {
        sysMenu.setDeep(2);
        sysMenuService.save(sysMenu);
        return Rest.ok();
    }

    /**
     * 编辑菜单
     */
    @RequiresPermissions("editMenu")
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        SysMenu sysMenu = sysMenuService.getById(id);
        model.addAttribute("sysMenu", sysMenu);

        if (sysMenu.getDeep() == 1) {
            return "/system/menu/edit";
        } else if (sysMenu.getDeep() == 2) {
            QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
            ew.orderBy(true, true, "code");
            ew.eq("pid", "0");
            List<SysMenu> list = sysMenuService.list(ew);
            model.addAttribute("list", list);
            return "/system/menu/editMenu";
        } else {
            QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
            ew.orderBy(true, true, "code");
            ew.eq("pid", "0");
            List<SysMenu> list = sysMenuService.list(ew);
            model.addAttribute("list", list);
            model.addAttribute("pSysMenu", sysMenuService.getById(sysMenu.getPid()));
            return "/system/menu/editAction";
        }
    }

    /**
     * 执行编辑菜单
     */
    @RequiresPermissions("editMenu")
    @Log("编辑菜单")
    @RequestMapping("/doEdit")
    @ResponseBody
    public Rest doEdit(SysMenu sysMenu, Model model) {
        sysMenuService.updateById(sysMenu);
        return Rest.ok();
    }

    /**
     * 执行编辑菜单
     */
    @RequiresPermissions("deleteMenu")
    @Log("删除菜单")
    @RequestMapping("/delete")
    @ResponseBody
    public Rest delete(String id) {
        sysMenuService.removeById(id);
        return Rest.ok();
    }

    /**
     * 根据父节点获取子菜单
     */
    @RequestMapping("/json")
    @ResponseBody
    public Rest json(String pid) {
        EntityWrapper<SysMenu> ew = new EntityWrapper<SysMenu>();
        ew.orderBy("sort");
        ew.addFilter("pid = {0} ", pid);
        List<SysMenu> list = sysMenuService.selectList(ew);

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (SysMenu m : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", m.getId());
            map.put("text", StringUtils.join(m.getCode(), "-", m.getMenuName()));
            listMap.add(map);
        }
        return Rest.okData(listMap);
    }


    /**
     * 验证菜单资源名称是否存在
     */
    @RequestMapping("/checkMenuResource")
    @ResponseBody
    public Rest checkMenuResource(String resource) {

        List<SysMenu> list = sysMenuService.list(new QueryWrapper<>().addFilter("resource = {0}", resource));
        if (list.size() > 0) {
            return Rest.failure("资源已存在,请换一个尝试.");
        }
        return Rest.ok();
    }

    @RequiresPermissions("addMenu")
    @Log("新增功能菜单")
    @RequestMapping("/doAddAction")
    public String doAddAction(SysMenu sysMenu, Model model) {
        sysMenu.setDeep(3);
        sysMenuService.save(sysMenu);
        return redirectTo("/system/menu/list/1.html");
    }
}
