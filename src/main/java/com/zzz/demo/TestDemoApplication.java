package com.zzz.demo;

import com.zzz.demo.config.SwaggerConfig;
import com.zzz.demo.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.zzz.demo.mapper")
public class TestDemoApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TestDemoApplication.class);
    }

    @Bean
    public ApplicationRunner run(WebServerApplicationContext applicationContext) {
        return args -> {
            System.out.println(applicationContext.getWebServer().getClass().getName());
        };
    }


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        SwaggerConfig swaggerConfig = applicationContext.getBean(SwaggerConfig.class);
        String property = applicationContext.getEnvironment().getProperty("swagger.properties");
        User user = applicationContext.getBean(User.class);
    }
}
