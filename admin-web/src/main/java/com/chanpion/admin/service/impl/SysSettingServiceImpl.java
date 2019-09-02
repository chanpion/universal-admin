package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.vacomall.entity.SysSetting;
import com.vacomall.mapper.SysSettingMapper;
import com.vacomall.service.ISysSettingService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * SysSetting 表数据服务层接口实现类
 *
 */
@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysSetting> implements ISysSettingService {
	
	@Cacheable(value = "settingCache")
	@Override
	public List<SysSetting> findAll() {
		// TODO Auto-generated method stub
		return this.selectList(new EntityWrapper<SysSetting>().orderBy("sort",true));
	}


}