package com.yg.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    
    // 配置用户和角色，Realm 可以是自定义的 Realm，也可以是 Shiro 提供的 Realm
    @Bean
    public Realm realm(){
        // 为简单起见，本案例没有配置数据库连接，直接配置了两个用户，以及对应的角色
        TextConfigurationRealm realm=new TextConfigurationRealm();
        realm.setUserDefinitions("sang=123,user\n admin=123,admin");

        // 配置角色权限，admin 具有read、write 权限，user 只有 read 权限
        realm.setRoleDefinitions("admin=read,write\n user=read");

        return realm;
    }

    // 配置基本的过滤规则
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition chainDefinition=new DefaultShiroFilterChainDefinition();
        // /login 和 /doLogin 可以匿名访问
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/doLogin", "anon");
        // /logout 是一个注销登录请求
        chainDefinition.addPathDefinition("/logout", "logout");
        // 其余请求则都需要人周后才能访问
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }


    // 这个Bean是为了支持在Thymeleaf中使用Shiro标签
    // 如果不在Thymeleaf中使用Shiro标签，那么可以不提供 ShiroDialect
    @Bean
    public ShiroDialect shiroDialect(){
        return new    ShiroDialect();
    }
}
