package com.zzz.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "demo-app")
public interface ITestService {

    @RequestMapping(value = "/test/testSelect", method = RequestMethod.GET)
    List<Object> testSelect();

}
