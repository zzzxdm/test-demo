package com.zzz.controller;

import com.zzz.consumer.utils.RedisUtil;
import com.zzz.entity.Goods;
import com.zzz.service.GoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "ESController", description = "ElasticSearch控制层")
@RestController
public class ESController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/addGood")
    public String addGood() {
        Goods goods = new Goods();
        goods.setId(redisUtil.getUniqekey("goods"));
        goods.setCategory("零食");
        goods.setDesc("这是一本好书");
        goods.setName("JAVA编程思想");
        goods.setPrice(99.9);
        goods.setInventory(100L);
        goods.setAddTime(LocalDateTime.now());
        goodsService.addGoods(goods);
        return "ok";
    }

    @GetMapping("/listByKeyword")
    public List<Goods> listByKeyword(String keyword) {
        return goodsService.listByKeyword(keyword);
    }

}
