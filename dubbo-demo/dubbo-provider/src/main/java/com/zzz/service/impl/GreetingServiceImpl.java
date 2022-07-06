package com.zzz.service.impl;

import com.zzz.service.GreetingService;

public class GreetingServiceImpl implements GreetingService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}