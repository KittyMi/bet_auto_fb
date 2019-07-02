package com.auto.ball;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * 项目启动类
 * @author Ady_L
 *
 */
@EnableTransactionManagement
@SpringBootApplication
@Slf4j
@ComponentScan(
        basePackages = {
        "com.auto.ball.**"}
        )
@EnableScheduling
public class Bootstrap extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(Bootstrap.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        log.info("Bootstrap is success!");
    }

}
