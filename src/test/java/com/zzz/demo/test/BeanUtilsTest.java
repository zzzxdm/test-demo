package com.zzz.demo.test;

import com.zzz.demo.bpc.BeanCopierUtils;
import com.zzz.demo.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ForkJoinTest.class)
@EnableAutoConfiguration
@Slf4j
public class BeanUtilsTest {

    @Test
    public void testCopy() {
        StopWatch stopWatch = new StopWatch();
        Article article = new Article();
        article.setTitle("title");
        article.setProvince("henan");
        article.setPrice(10);
        article.setAuthor("zzz");
        article.setCountryCode("test");

        List<Article> articleList = new ArrayList<>();
        int count = 100000;
        stopWatch.start();
        apacheCopy(article,articleList,count);
        stopWatch.stop();
        log.info("apacheCopy耗时:{}",stopWatch.getTotalTimeSeconds());

        stopWatch.start();
        springCopy(article,articleList,count);
        stopWatch.stop();
        log.info("springCopy耗时:{}",stopWatch.getTotalTimeSeconds());

        stopWatch.start();
        beanCopyer(article,articleList,count);
        stopWatch.stop();
        log.info("beanCopyer耗时:{}",stopWatch.getTotalTimeSeconds());

        stopWatch.start();
        beanCopyer1(article,articleList,count);
        stopWatch.stop();
        log.info("beanCopyer1耗时:{}",stopWatch.getTotalTimeSeconds());
    }

    private void springCopy(Article article, List<Article> articleList, int count) {
        articleList.clear();
        for (int i = 0; i < count; i++) {
            Article article1 = new Article();
            BeanUtils.copyProperties(article,article1);
            articleList.add(article1);
        }
        log.info("集合大小:{}",articleList.size());
    }

    private void beanCopyer(Article article, List<Article> articleList, int count) {
        articleList.clear();
        for (int i = 0; i < count; i++) {
            Article article1 = new Article();
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(article1,article);
                articleList.add(article1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        log.info("集合大小:{}",articleList.size());
    }

    private void apacheCopy(Article article, List<Article> articleList, int count) {
        articleList.clear();
        for (int i = 0; i < count; i++) {
            BeanCopier beanCopier = BeanCopier.create(article.getClass(), Article.class, false);
            Article newArticle = new Article();
            beanCopier.copy(article,newArticle,null);
            articleList.add(newArticle);
        }
        log.info("集合大小:{}",articleList.size());
    }

    private void beanCopyer1(Article article, List<Article> articleList, int count) {
        articleList.clear();
        for (int i = 0; i < count; i++) {
            Article newArticle = new Article();
            BeanCopierUtils.copyProperties(article,newArticle);
            articleList.add(newArticle);
        }
        log.info("集合大小:{}",articleList.size());
    }

}
