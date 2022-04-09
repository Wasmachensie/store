package com.cy.store.service.ex;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/09/11:46
 * @Description:
 * 用户没找到
 */
public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
