package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Author: 8Nuyoah
 * @Date: 2022/04/07/18:21
 * @Description:
 * 用户模块的持久层接口
 */

 /*
 * @Mapper这样写有缺陷：因为一个项目有很多mapper接口，这样写会使得代码臃肿，
 * 在启动类使用@@MapperScan("com.cy.store.mapper")注解，
 *在项目启动的时候会自动扫描所有的mapper接口，
 *并且把它们的实现类交给spring容器管理，
 *这样就可以在项目中获取到这些接口的实现类了
 * */
public interface UserMapper {

    /**
    * 插入用户数据
    * @Param: [user]用户
    * @Return: 受影响的行数
    * (增删改都是受影响的行数作为返回值，根据返回值判断是否执行成功)
    * */
    Integer insert(User user);

    /**
    * 根据用户名查询用户信息
    * @Param: username用户名
    * @Return: 如果找到对应的用户信息，返回用户信息，否则返回null
    * */
    User findByUsername(String username);

    /**
     * 根据用户的uid来修改密码
     * @Param: uid用户id
     * @Param: password用户输入的新密码
     * @Param: modifiedUser修改人
     * @Param: modifiedTime修改时间
     * @return: 受影响的行数
     */
    Integer updatePasswordByUid(@Param("uid")Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser")String modifiedUser,
                                @Param("modifiedTime")Date modifiedTime);
    /*用注解来简化xml配置，@Param注解的作用是给参数命名，
    参数命名后就能根据名字得到参数值，正确的将参数传入sql语句中。
    @Param("参数名")注解中的参数名需要和sql语句中的#{参数名}的参数名保持一致。*/

    /**
     * 根据用户uid来查询用户的数据
     * @Param: uid用户id
     * @return: 如果找到对应的用户信息，返回用户信息，否则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据用户uid来修改用户信息
     * @param: user 用户信息
     * @return: 受影响的行数
     */
    Integer updateInfoByUid(User user);


}
