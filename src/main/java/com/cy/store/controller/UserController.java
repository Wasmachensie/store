package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/17:58
 * @Description:
 */
//@Controller//交给spring管理
@RestController//=@Controller+@ResponseBody
@RequestMapping("users")//映射到users
public class UserController extends BaseController {
    //依赖业务层接口
    @Autowired
    private IUserService userService;

    /*
    * 1.接收数据方式：
    * 请求处理方法的参数列表设置为pojo类型来接收前端传递过来的数据
    * SpringBoot会将前端的url地址中的参数名和pojo类的属性进行比较，
    * 如果这两个名称相同，则自动注入到pojo类的属性上
    * */
    //优化后写法
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /*
    * 2.接收数据方式：
    * 请求处理方法的参数列表设置为非pojo类型，
    * SpringBoot会将请求的参数名和参数值进行比较，如果名称相同，则自动注入到参数列表中
    * */
    @RequestMapping("login")
    public JsonResult<User> login(String username,String password){
        User data = userService.login(username, password);
        return new JsonResult<>(OK,data);
    }


    /*@RequestMapping("reg")
    //ResponseBody//表示此方法的响应结果以Json格式进行数据响应给到前端
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        //调用业务层实现注册功能
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicationException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("在注册过程中产生未知异常，注册失败");
        }
        //返回结果
        return result;
    }*/
}
