package org.example.learingss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.learingss.mapper")
public class LearingSsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearingSsApplication.class, args);
    }

}
