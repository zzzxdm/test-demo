package com.zzz.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("kafka")
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private volatile boolean running = true;

    @RequestMapping("send")
    public String send(String msg) {
        new Thread(() -> {
            running = true;
            while (running) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(LocalDateTime.now());
                kafkaTemplate.send("test", String.format("当前时间:%s,消息内容:%s", currentTime, msg));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "success";
    }

    @GetMapping("/stop")
    public String stop() {
        running = false;
        return "success";
    }
}
