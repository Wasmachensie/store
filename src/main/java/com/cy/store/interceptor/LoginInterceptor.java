package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/09/16:47
 * @Description:
 * 定义一个拦截器，用于拦截请求，判断用户是否登录
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid数据，有就放行，没有就重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url+controller:映射)
     * @return 如果返回值为true，则放行，如果为false就拦截当前的请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //通过HttpServletRequest对象来获取全局session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null) {
            //说明用户没有登录，重定向到登录页面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        //说明用户已经登录，放行
        return true;
    }
}
