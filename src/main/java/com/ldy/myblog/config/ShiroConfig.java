package com.ldy.myblog.config;

import com.ldy.myblog.shiro.realm.AccountRealm;
import com.ldy.myblog.shiro.session.AccountSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 创建 realm
    @Bean
    public AccountRealm accountRealm(){
        return new AccountRealm();
    }

    // 创建安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        // 将自定义缓存管理器设置到安全管理器中
        securityManager.setCacheManager(cacheManager());
        // 将自定义会话管理器设置到安全管理器中
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    // 创建 shiro 的安全工厂
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 设置过滤器集合来实现权限控制
        Map<String,String> map = new HashMap<>();
        map.put("/**","anon");
        map.put("/logout","authc");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    // 开启对 shiro 的注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /* redis 有关 */

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    // 1、redis 的控制器，操作 redis
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        return redisManager;
    }

    // 2、sessionDAO
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    // 3、会话管理
    public DefaultWebSessionManager sessionManager(){
        AccountSessionManager sessionManager = new AccountSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    // 4、缓存管理
    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

    // 5、将安全管理器中的会话管理替换为自定义的会话管理器
}
