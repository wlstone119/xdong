package com.xdong;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.xdong.banner.EndBanner;
import com.xdong.banner.EndImageBanner;

/**
 * @ServletComponentScan 使用代码注册bean时不需要增加此注解
 * springboot自定义serlvet，filter，listener时使用@WebServlet、@WebFilter、@WebListener注解自动注册
 * @EnableTransactionManagement 开启注解事务管理，等同于xml配置方式的 <tx:annotation-driven /> 代码中使用@Transactional进行事务控制
 * 类XdongApplication.java的实现描述：TODO 类实现描述
 * @author wanglei 2018年2月2日 上午9:51:27
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.xdong.dal.*.dao")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class XdongApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(XdongApplication.class, args);
        // new EndBanner().printBanner(System.out);
        // new EndImageBanner().printBanner(System.out);
    }

}
