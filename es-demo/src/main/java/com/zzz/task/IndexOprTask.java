package com.zzz.task;

import com.zzz.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author zzz
 * @Date 2023/5/26
 * @Version V1.0
 */
@Slf4j
@Component
public class IndexOprTask implements ApplicationRunner {

    @Resource
    ElasticsearchRestTemplate esTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initGoodsIndex();
    }

    public void initGoodsIndex() {
        if (!esTemplate.indexOps(Goods.class).exists()) {
            esTemplate.indexOps(Goods.class).create();
            esTemplate.indexOps(Goods.class).putMapping();
        } else {
            log.info("index idx_goods exists.....");
        }
    }
}
