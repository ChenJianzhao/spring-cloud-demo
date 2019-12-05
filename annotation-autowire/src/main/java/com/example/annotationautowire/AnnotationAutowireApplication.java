package com.example.annotationautowire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自定义注解实现多实现类 Bean 的选择性注入
 */
@SpringBootApplication
public class AnnotationAutowireApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationAutowireApplication.class, args);
    }


}
