package com.jiangcl.alipay.demo.service;

/**
 * @author jiangcl
 * @date 2020/9/10
 * @desc
 */
public interface AlipayService {

    String payOrder(String orderAmount) throws Exception;
}
