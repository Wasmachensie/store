package com.cy.store.service.ex;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/16:31
 * @Description:
 * 业务层异常的基类
 * 为了便于统一管理自定义异常，应先创建service.ex.ServiceException自定义异常的基类异常，
 * 继承自RuntimeException类，并从父类生成子类的五个构造方法。
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
