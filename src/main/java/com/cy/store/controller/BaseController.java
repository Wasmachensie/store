package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/18:23
 * @Description:
 * 控制层类的基类
 * 在控制层抽离出来一个父类，在父类中统一处理关于异常的操作
 */
public class BaseController{

    //操作成功状态码
    public static final int OK = 200;

    //操作失败就要统一处理
    /*这个注解用来统一处理抛出的异常
    * 会自动将异常传递给此方法的参数列表上，所以需要用Throwable e接受
    *
    * 当项目中产生异常会被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，
    * 方法的返回值直接给到前端*/
    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    //请求处理方法，这个方法的返回值就是需要返回给前端的数据,数据的包装类型是JsonResult
    public JsonResult<Void> handlerException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicationException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户名不存在");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("密码错误");
        } else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("在注册过程中产生未知异常，注册失败");
        } else if (e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("在更新过程中产生未知异常，注册失败");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }
}
