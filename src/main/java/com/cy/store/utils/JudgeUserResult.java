package com.cy.store.utils;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/12/10:31
 * @Description:
 */
public class JudgeUserResult {
    @Autowired
    private UserMapper userMapper;

    public JudgeUserResult(Integer uid) {
    User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
        throw new UserNotFoundException("用户未找到");
        }
    }

}
