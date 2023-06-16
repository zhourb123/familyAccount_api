package com.zhourb.familyaccount_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan(basePackages = "com.zhourb.familyaccount_api.module.*")
@EnableTransactionManagement
@EnableScheduling
public class FamilyAccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyAccountApiApplication.class, args);
    }

}
