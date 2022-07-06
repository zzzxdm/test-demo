package com.cjs.example.mapper;

import com.cjs.example.entity.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/5/7 0007
 */
public interface MemberMapper {

    @Insert("insert into member values(#{name}, #{age}, #{email})")
    int insert(Member member);

    @Select("select name, age, email from member where 1 = 1")
    List<Member> listAll();
}
