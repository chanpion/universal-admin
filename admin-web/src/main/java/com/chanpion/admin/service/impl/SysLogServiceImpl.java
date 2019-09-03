package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.entity.SysLog;
import com.chanpion.admin.mapper.SysLogMapper;
import com.chanpion.admin.service.ISysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * SysLog 表数据服务层接口实现类
 *
 * @author April Chen
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Override
    public void insertLog(String title, String uname, String url, String parms) {
        // TODO Auto-generated method stub
        SysLog sysLog = new SysLog();
        sysLog.setCreateTime(new Date());
        sysLog.setTitle(title);
        sysLog.setUserName(uname);
        sysLog.setUrl(url);
        sysLog.setParams(parms);
        super.save(sysLog);
        logger.debug("记录日志:" + sysLog.toString());
    }


}