package com.example.database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.example.database.dao")
public class DatabaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(DatabaseApplication.class, args);
    }

}
