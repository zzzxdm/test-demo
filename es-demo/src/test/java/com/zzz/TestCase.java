package com.zzz;

import org.junit.Test;

/**
 * @author ZZZ
 * @version 1.0
 * @date 2019/10/25 0025
 */
public class TestCase {

    @Test
    public void testEquals() {
        Integer a = new Integer(20);
        Integer b = new Integer(20);
        System.out.println(a == b);
    }

    public static void main(String[] args) {
        Integer a = 200;
        Integer b = 200;
        System.out.println(a == b);
    }
}
