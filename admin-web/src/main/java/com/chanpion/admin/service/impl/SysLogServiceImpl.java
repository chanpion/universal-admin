package com.chanpion.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chanpion.admin.entity.SysLog;
import com.chanpion.admin.mapper.SysLogMapper;
import com.chanpion.admin.service.ISysLogService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * SysLog 表数据服务层接口实现类
 *
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

	public static final Logger logger = Logger.getLogger(SysLogServiceImpl.class);
	
	@Override
	public void insertLog(String title, String uname, String url, String parms) {
		// TODO Auto-generated method stub
		SysLog sysLog  =new SysLog();
		sysLog.setCreateTime(new Date());
		sysLog.setTitle(title);
		sysLog.setUserName(uname);
		sysLog.setUrl(url);
		sysLog.setParams(parms);
		super.insert(sysLog);
		logger.debug("记录日志:"+sysLog.toString());
	}


}