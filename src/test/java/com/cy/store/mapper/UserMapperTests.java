package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/07/20:37
 * @Description:
 */
//表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
/*
 * @RunWith:表示启动这个单元测试类(单元测试类不能够运行的)，
 * 需要传递一个参数必须是SpringRunner的实例类型*/
@RunWith(SpringRunner.class)
public class UserMapperTests {
    //用mapper层的接口来访问这两个方法是否正确
    /*
     * userMapper报错是因为：
     * idea有检测功能，接口是不能够直接创建Bean的(动态代理技术解决)
     * */
    @Autowired
    private UserMapper userMapper;

    /*
     * 单元测试方法：
     * 1.必须被@Test注解修饰
     * 2.返回值必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public*/
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("m1234");
        user.setPassword("123");
        //传递给insert方法,观察影响的行数
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("张三");
        System.out.println(user);
    }

    @Test
    //根据用户的uid来修改密码
    public void updatePassword(){
        userMapper.updatePasswordByUid(9,"123456",
                "testmodi",new Date());

    }

    @Test
    //根据用户uid来查询用户的数据
    public void findByUid() {
        User user = userMapper.findByUid(9);
        System.out.println(user);
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(16);
        user.setPhone("16666666666");
        user.setEmail("mmm@166.com");
        user.setGender(0);
        userMapper.updateInfoByUid(user);

    }
}
