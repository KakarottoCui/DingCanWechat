package com.gdpu.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.gdpu.realm.UserRealm;
import lombok.Data;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(value = {SecurityManager.class})
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroAutoConfiguration {
    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    private String hashALgorithmName = "md5";   //加密方式
    private int hashIterations = 2;     //散列次数
    private String loginURL = "/index.html";    //默认登陆页面

    private String[] anonUrls;
    private String  logoutUrl;
    private String[] authcUrls;

    //subject：主体，理解为用户,可能是程序，都要去访问系统的资源，系统需要对subject进行身份认证。
    //
    //principal：身份信息，通常是唯一的，一个主体还有多个身份信息，但是都有一个主身份信息（primary principal）
    //
    //credential：凭证信息，可以是密码 、证书、指纹。
    /*声明凭证匹配器*/
    @Bean("credentialsMacher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashALgorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }
    /*声明userRealm*/
    @Bean("userRealm")
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }
    /*配置SecurityManager*/
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       //注入userRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /*配置shiro过滤器*/
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        //设置未登录的时候
        factoryBean.setLoginUrl(loginURL);
        Map<String,String> filterChainDefinitionMap = new HashMap<>();
        //设置放行的路径
        if(anonUrls !=null && anonUrls.length>0){
            for(String anon : anonUrls){
                filterChainDefinitionMap.put(anon,"anon");
            }
        }
        //设置放行的路径
        if (null != logoutUrl){
            filterChainDefinitionMap.put(logoutUrl,"logout");
        }
        //设置放行的路径
        if(authcUrls != null && anonUrls.length>0){
            for(String authc : authcUrls){
                filterChainDefinitionMap.put(authc,"authc");
            }
        }
        Map<String, Filter>  filters = new HashMap<>();
        //使用前后端分离需要建立ShiroLoginFilter继承FormAuthenticationFilter
//        filters.put("authc",new ShiroLoginFilter());
        //配置过滤器
        factoryBean.setFilters(filters);
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }
    /*注册shiro的委托继承器，相当于之前在web.xml里面配置的
    *
    * @Return
    * */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxyFilter(){
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    //加入注解的使用，不加入这个注解不生效-- 开始
    /*
    *   @param securityManager
    *   @return
    * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /*  加入注解的使用，不加入这个注解不生效--结束*/
    /*
    *   这里是为了能在html页面引用shiro标签，上面这两个函数必须添加，不然会报错
    *
    *   @return
    * */

    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
