package com.zzz;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;
import com.zzz.entity.Student;
import com.zzz.service.RedisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("name:" + name);

        Student stu = service.getStudent("001");
        System.out.println(stu);
    }


    @RestController
    class TestController {

        @PostMapping("/testRecvData")
        public void testRecvData(@RequestBody JSONObject jsonObject) {
            System.out.println(jsonObject.toJSONString());
        }


        @PostMapping("/testRecvData1")
        public void testRecvData1(HttpServletRequest request) throws IOException {
            ServletInputStream inputStream = request.getInputStream();
            String data = new String(ByteStreams.toByteArray(inputStream), StandardCharsets.UTF_8);
            String data1 = new String(data.getBytes(Charset.defaultCharset()));
//            System.out.println(data);
//            data = unicodeDecode(data);
            System.out.println(data);
        }


        /**
         * @param string
         * @return 转换之后的内容
         * @Title: unicodeDecode
         * @Description: unicode解码 将Unicode的编码转换为中文
         */
        public String unicodeDecode(String string) {
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(string);
            char ch;
            while (matcher.find()) {
                ch = (char) Integer.parseInt(matcher.group(2), 16);
                string = string.replace(matcher.group(1), ch + "");
            }
            return string;
        }

    }

}
