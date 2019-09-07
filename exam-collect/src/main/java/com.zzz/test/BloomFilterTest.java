package com.zzz.test;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterTest {

    public static void main(String[] args) {
        final BloomFilter<String> dealIdBloomFilter = BloomFilter.create(new Funnel<String>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void funnel(String arg0, PrimitiveSink arg1) {
                arg1.putString(arg0, Charsets.UTF_8);
            }
        }, 1024 * 1024 * 32, 0.0000001d);

        dealIdBloomFilter.put("aaa");
        dealIdBloomFilter.put("bbb");
        dealIdBloomFilter.put("ccc");
        boolean contain = dealIdBloomFilter.mightContain("aaa");
        System.out.println(contain);
    }
}
