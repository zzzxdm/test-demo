package com.zzz.controller;

import com.zzz.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQController {

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping("/sendMsg")
    public String sendMsg(@RequestParam String msg) {
        msgProducer.sendMsg(msg);
        return "send success";
    }
}
