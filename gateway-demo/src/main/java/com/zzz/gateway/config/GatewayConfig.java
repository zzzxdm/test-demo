package com.zzz.gateway.config;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/9/3 0003
 */
@Configuration
@EnableDiscoveryClient
public class GatewayConfig {

    // 每秒钟通过请求数
    @Value("${gateway.default-replenish-rate:100}")
    private int defaultReplenishRate;
    // 缓冲令牌数
    @Value("${gateway.default-burst-capacity:150}")
    private int defaultBurstCapacity;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route1", r -> r
                        .path("/baidu")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()))
                        )
                        .uri("https://www.baidu.com/"))
                .route("path_route2", r -> r
                        .path("/api/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()))
                                .hystrix(c -> c.setSetter(HystrixObservableCommand.Setter
                                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("fallback"))
                                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                .withExecutionTimeoutInMilliseconds(30000)
                                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(500)
                                                .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                                        ))
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri("lb://gateway-demo"))
                .build();
    }

    @Bean
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        RedisRateLimiter redisRateLimiter = new RedisRateLimiter(defaultReplenishRate, defaultBurstCapacity);
        return redisRateLimiter;
    }
}
