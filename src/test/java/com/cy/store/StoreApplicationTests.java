package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired//自动装配
    private DataSource dataSource;

    //测试数据源是否正常
    /*
    * 数据库连接池：
    * 3.Hikari：管理数据库的连接对象
    * HikariProxyConnection@2096842550 wrapping com.mysql.cj.jdbc.ConnectionImpl@2a097d77
    * */
    @Test
    void getConnection() throws SQLException {
        // 连接时，抛出异常
        System.out.println(dataSource.getConnection());
    }
}
