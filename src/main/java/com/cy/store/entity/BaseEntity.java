package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/07/17:58
 * @Description:
 * 实体类的基类
 * 1.项目中许多实体类都会有日志相关的四个属性，
 * 所以在创建实体类之前，应先创建这些实体类的基类，
 * 将4个日志属性声明在基类中。
 * 在com.cy.store.entity包下创建BaseEntity类，作为实体类的基类。
 *
 * 只要一个类实现Serializable接口，那么这个类就可以序列化了。
 */
@Data
//因为这个基类的作用就是用于被其它实体类继承的，所以应声明为抽象类。
public class BaseEntity implements Serializable {
    //日志-创建人
    private String createdUser;
    //日志-创建时间
    private Date createdTime;
    //日志-最后修改执行人
    private String modifiedUser;
    //日志-最后修改时间
    private Date modifiedTime;
}
