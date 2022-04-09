package com.cy.store.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/17:45
 * @Description:
 * Json格式响应
 *
 * 只要一个类实现Serializable接口，那么这个类就可以序列化了。
 */
@Data
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据类型，用E泛型表示任何的数据类型
    private E Date;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    //出现异常时调用
    public JsonResult(Throwable e) {
        super();
        //获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E date) {
        super();
        this.state = state;
        this.Date = date;
    }

}
