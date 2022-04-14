package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/08/17:58
 * @Description:
 */
//@Controller//交给spring管理
@RestController
//=@Controller+@ResponseBody
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
    public JsonResult<User> login(String username,String password,HttpSession session){
        User data = userService.login(username, password);
        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<>(OK,data);
    }
    /**@Session 获取session对象
     * 相当于是一个工具类，获取session中的uid，有利于减少代码冗余
     * @return
     */
    public final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }
    public final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象中有四部分数据：username、email、phone、gender
        //uid数据需要再次封装到user对象中
        Integer uid = getuidFromSession(session);
        String usernameFromSession = getUsernameFromSession(session);
        userService.changeInfo(uid, usernameFromSession, user);
        return new JsonResult<>(OK);
    }

    //设置上传文件最大值为10M(常量要大写)
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /*
    * 限制上传文件的类型
    * public static final String[] AVATAR_TYPES =
                 new String[]{"image/jpeg", "image/png","image/gif"};
    * 数组也可以，但是为了以后能扩充用集合好*/
    public static final List<String> AVATAR_TYPES = new ArrayList<>();
    //静态初始化器：⽤于初始化本类的静态成员
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }
    /**
     * MultipartFile接口是springMVC提供的一个接口，这个接口为我们包装了
     * 获取文件类型的数据(任何类型的file都可以接收)，SpringBoot它整合了
     * SpringMVC,只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数即可，
     * 然后springBoot会自动将传递给服务的文件数据赋值给这个参数
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，
     * 如果名称不一致则可以使用@RequestParam注解进行标记和映射
     * 上传头像
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file")MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件类型是否正确
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值为true
        if (!AVATAR_TYPES.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件.../upload/文件.jpg
        //获取上下文的路径.真实(绝对)路径,返回值是串
        //String parent = session.getServletContext().getRealPath("upload");
        String parent = "D:\\Desktop\\SpringBoot电脑商城项目-V1.0\\pic";
        System.out.println(parent);
        //File对象指向这个路径,File是否存在
        File dir = new File(parent);
        //检测目录是否存在
        if (!dir.exists()) {
            //创建当前目录
            dir.mkdirs();
        }
        //获取这个文件的名称,使用UUID工具来生成一个新的字符串来作为文件名
        //例如:avatar01.jpg-->后缀要保存,前面的要用UUid生成随机字符串来保存
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        //获取文件后缀
        //找到小数点
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        //文件名拼接
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //在dir文件下创建一个filename文件
        File dest = new File(dir,filename);//是一个空文件
        //将参数file中的数据写入这个空文件中
        try {
            //将file文件中的数据写入到dest中，前提是文件后缀一致
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadIOException("文件读取异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }

        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径/upload/test.jpg
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid,username,avatar);
        //返回用户头像路径给前端页面，将来用于用户头像展示使用
        return new JsonResult<>(OK,avatar);
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
