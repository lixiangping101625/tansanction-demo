package com.distribute.tansanctiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value = "com.distribute.tansanctiondemo.dao")
public class TansanctionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TansanctionDemoApplication.class, args);
    }

}
