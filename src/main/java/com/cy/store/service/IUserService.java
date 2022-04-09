package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/16:40
 * @Description:
 * 用户模块业务层接口
 * 创建业务层接口目的是为了解耦
 */
public interface IUserService {
    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return 当前匹配用户数据，如果没有匹配的数据，则返回null
     */
     User login(String username,String password);
}
