package com.zzz;

import com.zzz.entity.Student;
import com.zzz.service.RedisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Resource
    RedisService service;

    @EventListener(ContextRefreshedEvent.class)
    public void test() {
        service.set("myname", "chhliu");
        Student s = new Student();
        s.setId("001");
        s.setName("chhliu");
        s.setGrade("一年级");
        s.setAge("28");
        service.set(s);

        String name = service.get("myname");
        System.out.println("name:"+name);

        Student stu = service.getStudent("001");
        System.out.println(stu);
    }
}
