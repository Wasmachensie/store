package com.cy.store.service.ex;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/16:37
 * @Description:
 * 用户名被占用异常
 */

public class UsernameDuplicationException extends ServiceException {
    public UsernameDuplicationException() {
        super();
    }

    public UsernameDuplicationException(String message) {
        super(message);
    }

    public UsernameDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDuplicationException(Throwable cause) {
        super(cause);
    }

    protected UsernameDuplicationException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
