package com.zzz.demo.intf.impl;

import com.zzz.demo.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ParllelStream extends AbstractCompute {

    @Override
    public void calculate() {
        super.initData(); // 初始化数据
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, Map<String, Map<String, DoubleSummaryStatistics>>> stringMapMap = articles.parallelStream()
//        Map<String, Map<String, Map<String, DoubleSummaryStatistics>>> stringMapMap = articles.stream()
                .collect(Collectors.groupingBy(Article::getTitle,
                        Collectors.groupingBy(Article::getProvince,
                                Collectors.groupingBy(Article::getAuthor,
                                        Collectors.summarizingDouble(Article::getPrice)))));
        List<Article> newList = new ArrayList<>();
        stringMapMap.forEach((title, map) -> {
            map.forEach((province, map1) -> {
                map1.forEach((author, count) -> {
                    Article article = new Article();
                    article.setAuthor(author);
                    article.setPrice(count.getSum());
                    article.setProvince(province);
                    article.setTitle(title);
                    newList.add(article);
                });
            });
        });
        stopWatch.stop();
        log.info("合并之后数据条数:{}", newList.size());
        log.info("并行流耗时:{}s", stopWatch.getTotalTimeSeconds());
    }
}