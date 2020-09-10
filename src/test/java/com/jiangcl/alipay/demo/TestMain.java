package com.jiangcl.alipay.demo;

import org.junit.jupiter.api.Test;

/**
 * @author jiangcl
 * @date 2020/9/4
 * @desc
 */
public class TestMain {
    @Test
    public void test1(){
        long total = 65004;
        double offset = 0.0;

        int i = 10240;

        offset = offset + i;

        double pro = offset/total;
        System.out.println(pro);
    }
}
