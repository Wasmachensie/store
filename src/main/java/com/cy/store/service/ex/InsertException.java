package com.cy.store.service.ex;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/16:38
 * @Description:
 * 在用户进行注册时，会执行数据库的INSERT操作，该操作也是有可能失败的。
 */
public class InsertException extends ServiceException {
    public InsertException() {
        super();
    }

    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertException(Throwable cause) {
        super(cause);
    }

    protected InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
