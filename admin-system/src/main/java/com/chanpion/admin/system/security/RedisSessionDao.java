package com.chanpion.admin.system.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author April Chen
 * @date 2019/9/10 20:32
 */
public class RedisSessionDao extends EnterpriseCacheSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    /**
     * session 在redis过期时间是30分钟30*60
     */
    private static int expireTime = 300;

    private static String prefix = "shiro:session:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建session，保存到数据库
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        return (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
    }

    /**
     * 更新session的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        logger.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
        redisTemplate.opsForValue().set(key, session, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 删除session
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        logger.debug("删除session:{}", session.getId());
        redisTemplate.delete(prefix + session.getId().toString());
    }
}
