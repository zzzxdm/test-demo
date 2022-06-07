package com.zzz.controller;

import com.zzz.entity.User;
import com.zzz.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author zzz
 * @Date 2022/4/22
 * @Version V1.0
 */
@RequestMapping("/")
public class TestController {

    @Resource
    RedisUtil redisUtil;

    @PostMapping("/putKey")
    @ResponseBody
    public String putKey(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUserNo())) {
            return "key must not empty";
        } else {
            if (redisUtil.set("user", user, 300)) {
                return "success";
            }
        }
        return "fail";
    }

    @PostMapping("/getKey")
    @ResponseBody
    public String getKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return "key must not empty";
        } else {
            User user = (User) redisUtil.get(key);
            return user.toString();
        }
    }


}
