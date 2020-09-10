package com.jiangcl.alipay.demo.service.impl;

import com.jiangcl.alipay.demo.config.AlipayProperties;
import com.jiangcl.alipay.demo.dto.AlipayBean;
import com.jiangcl.alipay.demo.service.AlipayService;
import com.jiangcl.alipay.demo.util.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiangcl
 * @date 2020/9/10
 * @desc
 */
@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private AlipayProperties alipayProperties;


    @Override
    public String payOrder(String orderAmount) throws Exception {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(System.currentTimeMillis() + "");
        alipayBean.setSubject("测试订单");
        alipayBean.setTotal_amount(orderAmount);
        alipayBean.setBody("");

        return AlipayUtil.pay(alipayProperties, alipayBean);
    }
}
