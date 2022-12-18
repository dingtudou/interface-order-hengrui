package com.cimc.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@EnableTransactionManagement
@ServletComponentScan
@EnableCaching
@EnableScheduling
@SpringBootApplication
@MapperScan({"com.cimc.order.*.mapper"})
public class InterfaceOrderHengruiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceOrderHengruiApplication.class, args);
    }

}
