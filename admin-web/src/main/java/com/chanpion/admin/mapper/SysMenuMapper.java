package com.chanpion.admin.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chanpion.admin.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * SysMenu 表数据库控制层接口
 *
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	List<String> selectMenuIdsByuserId(String uid);

	List<String> selectResourceByUid(@Param("uid") String uid);

}