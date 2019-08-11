package com.zzz.demo.bpc;


import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象转换器 2019/1/11 11:27
 *
 * @author wangliwei
 */
public class BeanCopierUtils {

    /**
     *
     */
    public volatile static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     *
     * @param source 原对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), true);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, new BpcConverterBeanCopier());
    }

    /**
     * 组装Map主键
     * @param sourceClazz 原对象字节码
     * @param targetClazz 目标对象字节码
     * @return key
     */
    private static String generateKey(Class<?> sourceClazz, Class<?> targetClazz) {
        return sourceClazz.toString() + targetClazz.toString();
    }

}
