package com.cy.store.service.ex;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/09/11:46
 * @Description:
 * 用户名没找到
 */
public class UsernameNotFoundException extends ServiceException {
    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFoundException(Throwable cause) {
        super(cause);
    }

    public UsernameNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
