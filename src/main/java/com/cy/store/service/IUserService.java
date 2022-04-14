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


    /**
     * 修改密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
     void changePassword(Integer uid,
                         String username,
                         String oldPassword,
                         String newPassword);

    /**
     * 根据uid查询用户数据
     * @param 传进来uid
     * @return: 返回User
     */
     User getByUid(Integer uid);

    /**
     * 更新用户数据
     * @param uid session中
     * @param username session中
     * @param user 用户数据
     */
     void changeInfo(Integer uid, String username,User user);

    /**
     * 修改用户头像
     * @param uid
     * @param username
     * @param avatar用户头像路径
     */
     void changeAvatar(Integer uid,String username,String avatar);
}
