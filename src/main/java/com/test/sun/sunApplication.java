package com.test.sun;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class sunApplication extends SpringBootServletInitializer {

public static void main(String[] args) {
        long time = System.currentTimeMillis();
        new SpringApplicationBuilder(sunApplication.class).run(args);
        System.out.println("启动成功,耗时: " + (System.currentTimeMillis() - time) / 1000 + "s");
        }

@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(sunApplication.class);
        }
        }