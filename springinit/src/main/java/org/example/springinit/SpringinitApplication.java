package org.example.springinit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.springinit.mapper")
public class SpringinitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringinitApplication.class, args);
    }

}
