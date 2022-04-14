package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import com.cy.store.utils.JudgeUserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/16:42
 * @Description:
 * 处理用户数据的业务层实现类
 */
@Service
//：将当前类的对象交给spring管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)方法查询用户名是否存在
        User result = userMapper.findByUsername(username);
        //对result进行判断，如果result不为null，说明用户名已经存在，抛出异常
        if (result != null) {
            //抛出异常
            throw new UsernameDuplicationException("此用户名已经存在");
        }
        /*此外还要进行密码加密处理：MD5算法的形式
        串+password+串---->MD5算法进行加密，一般连续加密三次
        这个串通常称为盐值，盐值是一个随机的字符串*/
        //拿到密码，记录
        String oldPassword = user.getPassword();
        //随机生成盐值--->转化为字符串---->密码加密一般大写
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);

        //将密码和盐值作为一个整体，进行加密处理
        String md5Pwd = getMD5Pwd(oldPassword, salt);
        //将加密后的密码设置到user对象中
        user.setPassword(md5Pwd);

        //补全数据：is_delete设置为0
        user.setIsDelete(0);
        //补全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现(row==1)
        Integer rows = userMapper.insert(user);
        //判断rows（插入的行数）是否是1，是，说明注册成功，否则注册失败
        if (rows != 1) {
            //抛出异常
            throw new InsertException("在注册过程中产生未知异常，注册失败");
        }
    }

    @Override
    public User login(String username, String password) {
        //调用findByUsername(username)方法查询用户名是否存在,如果不存在，抛出异常
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UsernameDuplicationException("用户名不存在");
        }
        //检测用户的密码是否匹配
        // 1.先获取到数据库中加密之后的密码
        String oldPwd = result.getPassword();
        // 2.和用户传递过来的密码进行比较
        //   2.1先获取盐值：上一次注册时候自动生成的盐值
        String salt = result.getSalt();
        //   2.2将用户传递过来的密码用相同的MD5算法加密规则进行加密
        String newPwd = getMD5Pwd(password, salt);
        // 3.将密码进行比较
        if (!newPwd.equals(oldPwd)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete字段是否为1，表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UsernameDuplicationException("用户数据不存在");
        }
        //以上三个都没问题开始进行用户数据查询
        //提升系统性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        //在登录的时候返回有用户的头像
        user.setAvatar(result.getAvatar());
        //将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用(uid,username,avatar)
        return user;

    }

    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            /* 有可能你登陆了放了五分钟别人登陆了注销了这个账号，
            * 你不知道，你进行密码修改，但你的账号已经没有了*/
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码与数据库中密码比较
        //先拿到原始密码
        String oldMD5Pwd = getMD5Pwd(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMD5Pwd)) {
            throw new PasswordNotMatchException("密码错误");
        }
        //将新密码设置到数据库中,先加密再去更新
        String newMD5Pwd = getMD5Pwd(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Pwd,
                username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }

    }

    @Override
    //根据uid查询用户数据
    public User getByUid(Integer uid) {
        //先判断这个用户存不存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户未找到");
        }
        /*把查询到的数据传递给前端页面，如果直接返回result可以，但是数据太重量了，
        * 所以创建一个中间过渡的空的user对象，把需要的数据封装到user对象中，在返回user对象*/
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    /**
     * User对象中的数据只有：phone、Email、Gender
     * 因为spring自动进行依赖注入时，只注册表单当中的值，
     * 所以还需要手动将uid和username封装
     */
    public void changeInfo(Integer uid, String username, User user) {
        //在点击修改信息的时候查询用户信息
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户未找到");
        }
        user.setUsername(username);
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        //建议先查询用户数据存不存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户未找到");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像产生未知异常");
        }
    }


    //定义一个md5加密方法
    private String getMD5Pwd(String password, String salt) {
        //for循环：连续加密三次
        for (int i = 0; i < 3; i++) {
        //md5加密算法的方法的调用
             password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
            //将密码和盐值作为一个整体，进行加密处理
        }
        //返回加密后的密码
        return password;
    }



}
