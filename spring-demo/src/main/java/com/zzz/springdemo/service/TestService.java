package com.zzz.springdemo.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

@Service
@Slf4j
public class TestService {


    ExecutorService executorService = Executors.newCachedThreadPool(new NamedThreadFactory("task-pool", false));
    ExecutorService testExecutor = Executors.newFixedThreadPool(1);

    @Resource
    private ThreadPoolExecutor messageConsumeDynamicExecutor;

    @Resource
    private ThreadPoolExecutor messageProduceDynamicExecutor;


    @Async
    @SneakyThrows
    public void testHello() {
        log.info("test hello");
        List<Callable<Object>> callables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable callable = () -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        TimeUnit.MICROSECONDS.sleep(RandomUtil.randomInt(500, 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("current time:{}", System.currentTimeMillis());
                }
                return null;
            };
           callables.add(callable);
        }

        List<Future<Object>> futures = messageConsumeDynamicExecutor.invokeAll(callables);
        futures.forEach(v -> {
            try {
                v.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

        log.info("hello done");
        String result = HttpUtil.get("http://localhost:8181/test/test");
        log.info("get result:{}", result);
    }

    public void testMultiThread() {
        testExecutor.submit(() -> {
            String mainThdName = Thread.currentThread().getName();
            IntStream.range(1,6).forEach(i -> {
                executorService.submit(() -> {
                    String subThdName = Thread.currentThread().getName();
                    System.out.println(StrUtil.format("{}-{}-{}-{}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), mainThdName, subThdName, i));
                });
                try {
                    TimeUnit.MILLISECONDS.sleep(RandomUtil.randomInt(1000, 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
