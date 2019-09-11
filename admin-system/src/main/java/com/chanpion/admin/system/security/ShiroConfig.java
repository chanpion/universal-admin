package com.chanpion.admin.system.security;

import com.chanpion.admin.system.utils.ShiroUtil;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author April Chen
 * @date 2019/9/6 17:20
 */
@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(ShiroUtil.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(ShiroUtil.HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new RedisSessionDao();
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(300000);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(cacheManager());
        sessionManager.getSessionListeners().add(new ShiroSessionListener());
        return sessionManager;
    }

    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(userRealm());
        webSecurityManager.setCacheManager(cacheManager());
        webSecurityManager.setSessionManager(sessionManager());
        return webSecurityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        shiroFilterChainDefinition.addPathDefinition("/adminlte/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/css/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/js/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/error/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/captcha", "anon");
        shiroFilterChainDefinition.addPathDefinition("/login", "anon");
        shiroFilterChainDefinition.addPathDefinition("/**", "authc");
        return shiroFilterChainDefinition;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
