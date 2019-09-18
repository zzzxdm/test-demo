package com.zzz.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "demo-app")
public interface IDemoService {

    @RequestMapping(value = "/test/hello", method = RequestMethod.GET)
    String hello();

}
