package com.zzz.demo.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class SpringUtils {

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static Object getBean(Class clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * SpringMvc下获取WebApplicationContext
     * @Desc
     * @author ZZZ
     * @date 2017年5月11日
     * @return
     */
    public static WebApplicationContext getApplicationContext(){
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    /**
     * @Desc  SpringMvc下获取request
     * @author ZZZ
     * @date 2017年5月11日
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
