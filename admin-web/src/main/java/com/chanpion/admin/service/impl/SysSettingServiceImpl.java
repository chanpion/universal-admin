package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.entity.SysSetting;
import com.chanpion.admin.mapper.SysSettingMapper;
import com.chanpion.admin.service.ISysSettingService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysSetting 表数据服务层接口实现类
 */
@Service
public class SysSettingServiceImpl extends ServiceImpl<SysSettingMapper, SysSetting> implements ISysSettingService {

    @Cacheable(value = "settingCache")
    @Override
    public List<SysSetting> findAll() {
        // TODO Auto-generated method stub
        return this.list(new QueryWrapper<SysSetting>().orderBy(false, true, "sort"));
    }

}