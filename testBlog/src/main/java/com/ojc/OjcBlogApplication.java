package com.ojc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.ojc.mapper")
@EnableSwagger2
@EnableScheduling       //启用定时任务
public class OjcBlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(OjcBlogApplication.class,args);
    }
}
