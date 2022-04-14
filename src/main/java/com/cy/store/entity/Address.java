package com.cy.store.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/14/15:18
 * @Description:
 * 收货地址数据的实体类
 */
 @Data
public class Address extends BaseEntity implements Serializable {
    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip;
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;

}
