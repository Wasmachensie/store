package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/07/18:06
 * @Description:
 * 用户实体类：SpringBoot约定大于配置
 * 基本类型建议用包装类完成，
 * 因为提供相关api，将来调用的时候可以调用来做一些逻辑上的判断
 *
 * 只要一个类实现Serializable接口，那么这个类就可以序列化了。
 */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

}
