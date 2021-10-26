package com.xuan.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xuan.entity.Account;
import com.xuan.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

//创建三个对象//编写认证和授权规则

@Configuration
public class shiroConfig {
    //    把自定义的realm创建出来，把realm对象注入到IoC中
    @Bean
    public AccountRealm accountRealm() {
        return new AccountRealm();
    }

    //  ioc中取出realm对象，然后注入manage中
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("accountRealm") AccountRealm accountRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);
        return manager;
    }

    // ioc中拿出manage对象，注入filterfcatory中——最终干事的
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

//        权限设置——xx请求对应xx权限，包括是否登陆，是否具有什么权限，是否具有什么角色(中括号中列出角色或者权限列表)
        Map<String, String> map = new Hashtable<>();
        map.put("/main", "authc");
        map.put("/manage", "perms[manage]");
        map.put("/administrator", "roles[administrator]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);//根据map设置过滤器，拦截请求
        shiroFilterFactoryBean.setLoginUrl("login");//设置登录页面，默认login.jsp，现在相当于发送一个login请求进入控制器，结合视图解析器转到login.html
        shiroFilterFactoryBean.setUnauthorizedUrl("unauth");//设置未授权的返回界面
        return shiroFilterFactoryBean;
    }

    //    引入方言，页面中才能使用
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
