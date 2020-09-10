package com.jiangcl.alipay.demo.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.jiangcl.alipay.demo.config.AlipayProperties;
import com.jiangcl.alipay.demo.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangcl
 * @date 2020/9/10
 * @desc
 */
@Controller
@RequestMapping("/order")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private AlipayProperties properties;

    @RequestMapping("/pay/page")
    public String payPage(){
        return "payPage";
    }

    @PostMapping("/pay")
    public void orderPay(String orderAmount, HttpServletResponse response){
        try {
            String result = alipayService.payOrder(orderAmount);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 支付完成后的跳转页面
     * @author jiangcl
     * @date 2020/9/10
     * @param
     * @return java.lang.String
     */
    @RequestMapping("/pay/success")
    public String successPage(){
        System.out.println("success");
        return "success";
    }

    /**
     * @desc 支付完成后的回调接口
     * @author jiangcl
     * @date 2020/9/10
     * @param
     * @return void
     */
    @RequestMapping("/pay/notifyPayResult")
    @ResponseBody
    public String notifyPayResult(HttpServletRequest request){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<进入支付宝回调>>>>>>>>>>>>>>>>>>>>>>>>>");
        // 1.从支付宝回调的request域中取值放到map中
        Map<String, String[]> requestParams = request.getParameterMap();

        Map<String, String> params = new HashMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        //2.封装必须参数
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        //交易状态
        String tradeStatus = params.get("trade_status");

        System.out.println("outTradeNo:" + outTradeNo + " tradeStatus:" + tradeStatus);

        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, properties.getPublicKey(), properties.getCharset(), properties.getSignType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------->验签结果:" + signVerified);

        //4.对验签进行处理

        if (signVerified) {
            //验签通过
            //只处理支付成功的订单: 修改交易表状态,支付成功
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
                //支付成功的逻辑处理
                System.out.println("支付成功");
                return "success";
            } else {
                //支付失败的逻辑处理
                System.out.println("支付失败");
                return "failure";
            }
        } else {
            //验签不通过
            System.err.println("-------------------->验签失败");
            return "failure";
        }
    }
}
