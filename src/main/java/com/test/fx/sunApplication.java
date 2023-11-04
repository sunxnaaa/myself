package com.test.fx;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class sunApplication extends SpringBootServletInitializer {

public static void main(String[] args) {

        long time = System.currentTimeMillis();
        try {
                new SpringApplicationBuilder(sunApplication.class).run(args);
        } catch (Exception e) {
                e.printStackTrace();
        }
        System.out.println("启动成功,耗时: " + (System.currentTimeMillis() - time) / 1000 + "s");
        }

@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(sunApplication.class);
                }
        }