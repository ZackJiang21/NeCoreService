package org.iiai.ne;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.zack.zblog.dao")
@EnableScheduling
public class NeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeApplication.class, args);
    }
}
