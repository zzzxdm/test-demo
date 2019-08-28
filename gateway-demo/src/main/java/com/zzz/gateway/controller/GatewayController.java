package com.zzz.gateway.controller;

import com.google.common.collect.Lists;
import com.zzz.gateway.bean.DynamicRouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/8/28 0028
 */
@RestController
public class GatewayController {

    @GetMapping("/test1")
    public Mono<String> test1() {
        return Mono.just("success");
    }


    @GetMapping("/test2")
    public Flux<List> test2() {
        List<String> arrayList = Lists.newArrayList("test1", "test2");
        return Flux.just(arrayList);
    }


    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @GetMapping("/routes")
    public Flux<RouteDefinition> routes() {
        return dynamicRouteService.getRouteDefinitions();
    }
}
