package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/09/17:06
 * @Description:
 * 处理器拦截器配置
 * WebMvcConfigurer接口中的核心方法之一
 * addInterceptors(InterceptorRegistry registry)方法表示添加拦截器。
 *                  主要用于进行用户登录状态的拦截，日志的拦截等。
 * - addInterceptor：需要一个实现HandlerInterceptor接口的拦截器实例
 * - addPathPatterns：用于设置拦截器的过滤路径规则；addPathPatterns("/**")对所有请求都拦截
 * - excludePathPatterns：用于设置不需要拦截的过滤规则
 */
@Configuration//加载当前拦截器并注册
public class LoginInterceptorConfig implements WebMvcConfigurer {
    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        //配置白名单：放在List<String>中，可以添加多个
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");//注册
        patterns.add("/web/login.html");//登录
        patterns.add("/web/index.html");//主页
        patterns.add("/web/product.html");//商品详情
        patterns.add("/users/reg");//注册
        patterns.add("/users/login");//登录

        registry.addInterceptor(interceptor)
                //要拦截的url是什么，/**表示所有的请求
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
