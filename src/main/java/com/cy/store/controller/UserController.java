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

    //优化后写法
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
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
