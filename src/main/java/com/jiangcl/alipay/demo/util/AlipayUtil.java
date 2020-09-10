package com.jiangcl.alipay.demo.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jiangcl.alipay.demo.config.AlipayProperties;
import com.jiangcl.alipay.demo.dto.AlipayBean;

/**
 * @author jiangcl
 * @date 2020/9/10
 * @desc
 */
public class AlipayUtil {

    public static String pay(AlipayProperties properties, AlipayBean alipayBean) throws Exception{
        // 1、获得初始化的AlipayClient
        String serverUrl = properties.getGatewayUrl();
        String appId = properties.getAppId();
        String privateKey = properties.getPrivateKey();
        String format = "json";
        String charset = properties.getCharset();
        String alipayPublicKey = properties.getPublicKey();
        String signType = properties.getSignType();
        String returnUrl = properties.getReturnUrl();
        String notifyUrl = properties.getNotifyUrl();
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        return alipayClient.pageExecute(alipayRequest).getBody();
    }
}
