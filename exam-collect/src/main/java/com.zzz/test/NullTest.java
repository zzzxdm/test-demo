package com.zzz.test;

import java.util.ArrayList;
import java.util.List;

public class NullTest {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        // null can not do anything...
        list.add(null);
        list.add(1L);
        int sum = 0;
        for (Long aLong : list) {
            sum += aLong;
        }
        System.out.println(sum);
    }
}
