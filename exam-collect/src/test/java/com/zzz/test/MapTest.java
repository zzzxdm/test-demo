package com.zzz.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTest {

    @Test
    public void mapTest() {
        Map<String, Object> map = new HashMap();
        map.put("name", "zzz");
        map.put("age", 27);
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey() + "----" + entry.getValue());
            // java.util.ConcurrentModificationException
            // map.put("test", "test");
        }
    }
}
