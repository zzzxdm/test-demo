package com.zzz.demo.test;

import com.zzz.demo.entity.Article;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamGroupTest {

    private static List<Article> articles = new ArrayList<Article>();

    static {
        Article a1 = new Article("Hello World", "Tom", "CN", "GD");
        Article a2 = new Article("Thank you teacher", "Bruce", "CN",
                "GX");
        Article a3 = new Article("Work is amazing", "Tom", "CN", "GD");
        Article a4 = new Article("New City", "Lucy", "US", "OT");
        a1.setPrice(1.0);
        a1.setPrice(2.0);
        a1.setPrice(3.0);
        a1.setPrice(4.0);
        articles.add(a1);
        articles.add(a2);
        articles.add(a3);
        articles.add(a4);
    }

    /**
     * 通过for循环逻辑，编程上会麻烦点，但是效率上高很多
     */
    private static void groupByCountryAndProvince_byNormal() {
        Map<String, Map<String, List<Article>>> result = new HashMap<String, Map<String, List<Article>>>();
        for (Article article : articles) {
            Map<String, List<Article>> pMap = result.get(article.getCountryCode());
            if(pMap==null) {
                pMap = new HashMap<String, List<Article>>();
                result.put(article.getCountryCode(), pMap);
            }
            List<Article> list = pMap.get(article.getProvince());
            if(list==null) {
                list = new ArrayList<Article>();
                pMap.put(article.getProvince(), list);
            }
            list.add(article);
        }
        result.forEach((cc, map) -> {
            System.out.println("Country Code is:" + cc);
            map.forEach((pc, list) -> {
                System.out.println("    Province Code is:" + pc);
                list.forEach((article) -> {
                    System.out.println("        Article titile is:" + article.getTitle() + ",author is:"
                            + article.getAuthor());
                });
            });
        });
    }

    /**
     * 以串行流的方式，通过Collectors做多维度的分组，非常方便，但是性能上很差
     */
    private static void groupByCountryAndProvince() {
        Map<String, Map<String, List<Article>>> result = articles.stream()
                .collect(Collectors.groupingBy(Article::getCountryCode,
                        Collectors.groupingBy(Article::getProvince)));
        result.forEach((cc, map) -> {
            System.out.println("Country Code is:" + cc);
            map.forEach((pc, list) -> {
                System.out.println("    Province Code is:" + pc);
                list.forEach((article) -> {
                    System.out.println("        Article titile is:" + article.getTitle() + ",author is:"
                            + article.getAuthor());
                });
            });
        });
    }

    /**
     * 以并行流的方式，通过Collectors做多维度的分组，性能上比串行流的效率就高很多了
     * 实现方式也很简单，只需要将stream()修改为parallelStream()实现。
     */
    private static void groupByCountryAndProvinceParallel() {
        Map<String, Map<String, DoubleSummaryStatistics>> result = articles.parallelStream()
                .collect(Collectors.groupingBy(Article::getCountryCode,
                        Collectors.groupingBy(Article::getProvince, Collectors.summarizingDouble(art -> art.getPrice())))
                );
        List<Article> articleList = new ArrayList<>();
        result.forEach((cc, map) -> {
            System.out.println("Country Code is:" + cc);
            map.forEach((pc, article) -> {
                System.err.println("Province Code is:" + pc + ":" + article.getCount());
                Article article1 = new Article();
                article1.setProvince(pc);
                article1.setPrice(article.getCount());
                article1.setCountryCode(cc);
                articleList.add(article1);
            });
        });
        for (Article art : articleList) {
            System.out.println(art.toString());
        }
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        groupByCountryAndProvince();
        long end = System.currentTimeMillis();
        System.out.println("串行流分组使用时长（毫秒）:" + (end - start)+"\n");

        start = System.currentTimeMillis();
        groupByCountryAndProvinceParallel();
        end = System.currentTimeMillis();
        System.out.println("并行流分组使用时长（毫秒）:" + (end - start)+"\n");

        start = System.currentTimeMillis();
//        groupByCountryAndProvince_byNormal();
        end = System.currentTimeMillis();
        System.out.println("普通分组使用时长（毫秒）:" + (end - start));
    }



}
