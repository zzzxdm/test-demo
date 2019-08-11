package com.zzz.demo.test;

import com.zzz.demo.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForkJoinTest.class)
@EnableAutoConfiguration
@Slf4j
public class ForkJoinTest {

    public List<Article> articles = new ArrayList<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setSerializer() {
        redisTemplate.setKeySerializer(RedisSerializer.string());
    }

    @Before
    public void initData() {
        String key = "test-data";
        ValueOperations operations = redisTemplate.opsForValue();
        if (operations.get(key) != null) {
            articles = (List<Article>) operations.get(key);
        } else {
            initTestData(articles);
            operations.set(key, articles);
        }
        log.info("测试数据条数:{}", articles.size());
    }

    private void initTestData(List<Article> articles) {
        int count = 1000000;
        Random random = new Random(1000);
        for (int i = 0; i < count; i++) {
            String title = "test" + random.nextInt(30);
            String author = "author" + random.nextInt(30);
            String province = "province" + random.nextInt(10);
            double price = random.nextDouble() * 100;
            DecimalFormat decimalFormat = new DecimalFormat("00.00");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String formatPrice = decimalFormat.format(price);
            Article article = new Article(province, title, author, Double.valueOf(formatPrice));
            articles.add(article);
        }
    }

    @Test
    public void test() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        doCalc(articles);
        stopWatch.stop();
        log.info("并行流耗时:{}s", stopWatch.getTotalTimeSeconds());
    }

    private void doCalc(List<Article> articles) {

        Map<String, Map<String, Map<String, DoubleSummaryStatistics>>> stringMapMap = articles.parallelStream()
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
        if (newList.size() != articles.size()) {
            doCalc(newList);
        } else {
            log.info("合并之后数据条数:{}", newList.size());
        }
    }
}
