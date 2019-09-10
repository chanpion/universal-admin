package com.chanpion.admin.system.security;

import com.chanpion.admin.system.config.Consts;
import com.chanpion.admin.system.entity.User;
import com.chanpion.admin.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * @author April Chen
 * @date 2019/9/6 16:35
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = authorizationInfo.getRoles();
        LOGGER.info("roles:{}", roles);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findByUsername(username);
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());
        setSession(Consts.LOGIN_USER_KEY, user);
        return authenticationInfo;
    }

    private void setSession(Object key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(key, value);
        LOGGER.debug("登录验证：{},session:{}", value, session.getId());
    }

}
