package com.cjs.example.service;

import com.cjs.example.annotation.Master;
import com.cjs.example.entity.Member;

import java.util.List;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/5/7 0007
 */
public interface MemberService {

    int insert(Member member);

    @Master
    int save(Member member);

    List<Member> selectAll();

    String getToken(String appId);
}
