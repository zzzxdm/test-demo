package com.zzz;

import com.zzz.entity.Student;
import com.zzz.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootRedisApplicationTests.class)
@ComponentScan(basePackages = "com.zzz")
public class SpringbootRedisApplicationTests {

	@Autowired
	private RedisService service;
 
	@Test
	public void contextLoads() {
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