package com.gw.das.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户诊断 微服务启动入口
 * @SpringBootApplication申明让spring boot自动给程序进行必要的配置，等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
 * @RestController返回json字符串的数据，直接可以编写RESTFul的接口
 * 在 SpringBootApplication 上使用@ServletComponentScan注解后，Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
 * @author: James.pu
 * @Email : James.pu@gwtsz.net
 * @Create Date: 2017年6月6日
 */
@RestController
@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
public class AppStarter {
    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }
}
