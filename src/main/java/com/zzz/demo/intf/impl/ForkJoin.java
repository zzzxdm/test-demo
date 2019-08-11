package com.zzz.demo.intf.impl;

import com.zzz.demo.entity.Article;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

@Slf4j
public class ForkJoin extends AbstractCompute {

    @Override
    public void calculate() {
        super.initData(); // 初始化数据
        //执行一个任务
        doCalc(articles);
    }

    private void doCalc(List<Article> articles) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ComputeTask computeTask = new ComputeTask(articles);
        ForkJoinTask<List<Article>> result = forkJoinPool.submit(computeTask);
        try {
            List<Article> articleList = result.get();
            if (articleList.size() == articles.size()) {
                log.info("合并之后数据条数:{}", articleList.size());
            } else {
                doCalc(articleList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ComputeTask extends RecursiveTask<List<Article>> {
        public static final int collSize = 1000000;
        private List<Article> articleList;

        public ComputeTask(List<Article> articles) {
            this.articleList  = articles;
        }

        @Override
        protected List<Article> compute() {
            List<Article> resultList = new ArrayList<>();
            ComputeTask left = null;
            ComputeTask right = null;
            if (articleList.size() > collSize) {
                int size = articleList.size();
                left = new ComputeTask(articleList.subList(0,size / 2));
                right = new ComputeTask(articleList.subList(size / 2, size));
                // 执行子任务
                left.fork();
                right.fork();
                // 合并结果
                List<Article> leftArticles = left.join();
                List<Article> rightArticles = right.join();
                resultList.addAll(leftArticles);
                resultList.addAll(rightArticles);
            } else {
                resultList =  doCompute(articleList);
            }
            return resultList;
        }

        private List<Article> doCompute(List<Article> data) {
            Map<String, Map<String, Map<String, DoubleSummaryStatistics>>> stringMapMap = data.parallelStream()
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
            log.info("合并之后大小:{}",newList.size());
            return newList;
        }
    }
}
