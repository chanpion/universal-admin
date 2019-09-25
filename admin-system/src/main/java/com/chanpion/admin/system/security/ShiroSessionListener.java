package com.chanpion.admin.system.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author April Chen
 * @date 2019/9/11 14:57
 */
public class ShiroSessionListener implements SessionListener {
    private static final Logger log = LoggerFactory.getLogger(ShiroSessionListener.class);

    @Override
    public void onStart(Session session) {
        log.debug("ShiroSessionListener session {} 被创建", session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.debug("ShiroSessionListener session {} 被销毁", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        if (session != null) {
            log.debug("ShiroSessionListener session {} 过期", session.getId());
        }
    }
}
