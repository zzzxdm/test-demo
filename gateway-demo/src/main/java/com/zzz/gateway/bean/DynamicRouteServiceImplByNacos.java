package com.zzz.gateway.bean;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zzz.gateway.utils.JsonUtils;
import com.zzz.gateway.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
//@Component
public class DynamicRouteServiceImplByNacos implements CommandLineRunner {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @Autowired
    private NacosGatewayProperties nacosGatewayProperties;

    /**
     * 监听Nacos Server下发的动态路由配置
     */
    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosGatewayProperties.getAddress());
            String content = configService.getConfig(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), nacosGatewayProperties.getTimeout());
            log.info("Load gateway config info from nacos:\n {}", StringUtil.replaceSpace(content));
            configService.addListener(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("Load changed gateway config info from nacos:\n {}", StringUtil.replaceSpace(configInfo));
                    List<RouteDefinition> list = JsonUtils.jsonToList(configInfo, RouteDefinition.class);
                    list.forEach(definition -> dynamicRouteService.update(definition));
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        dynamicRouteByNacosListener();
    }

}
