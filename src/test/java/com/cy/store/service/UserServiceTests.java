package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class UserServiceTests {
    @Autowired
    private IUserService userService;
    /*
    * 单元测试方法：
    * 1.必须被@Test注解修饰
    * 2.返回值必须是void
    * 3.方法的参数列表不指定任何类型
    * 4.方法的访问修饰符必须是public*/
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("mm");
            user.setPassword("123");
            userService.reg(user);//该方法无返回值
            System.out.println("OK");
        } catch (ServiceException e) {
            //e.printStackTrace();
            //获取类的对象，再获取异常类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常类的描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("test02", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(7,"test02", "123", "123456");
    }

    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(18));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("12345678901");
        user.setEmail("18181@qq.com");
        user.setGender(0);
        userService.changeInfo(18, "test18", user);
    }

}
