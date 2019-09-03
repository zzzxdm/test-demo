package com.zzz.test.courrent_test;

import jodd.util.collection.SortedArrayList;
import org.junit.Test;

import java.util.List;

public class SortedListTest {

    @Test
    public void sortedList() {
        List<String> list = new SortedArrayList();
        list.add("aaa");
        list.add("ccc");
        list.add("bbb");
        list.forEach(System.out::println);
    }
}
