package com.cjs.example.controller;

import com.alibaba.fastjson.JSON;
import com.cjs.example.entity.Member;
import com.cjs.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/5/7 0007
 */
@RestController
public class DemoController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/testWrite")
    public void testWrite() {
        Member member = new Member();
        int random = new Random().nextInt(100);
        member.setName("user" + random);
        member.setAge(random);
        memberService.insert(member);
    }

    @GetMapping("/testRead")
    public List<Member> testRead() {
        List<Member> memberList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            memberList = memberService.selectAll();
            System.out.println(JSON.toJSONString(memberList));
        }
        return memberList;
    }

    @GetMapping("/testSave")
    public void testSave() {
        Member member = new Member();
        member.setName("wangwu");
        memberService.save(member);
    }

    @GetMapping("/testReadFromMaster")
    public void testReadFromMaster() {
        memberService.getToken("1234");
    }
}
