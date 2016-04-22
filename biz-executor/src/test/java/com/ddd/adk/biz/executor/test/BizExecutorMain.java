/*
 * 修订记录：
 * aiping.yuan 9:39 创建
 */
package com.ddd.adk.biz.executor.test;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Created by aiping.yuan on 2016/4/22.<p>
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.ddd.adk")
public class BizExecutorMain extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BizExecutorMain.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
