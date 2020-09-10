package com.jiangcl.alipay.demo.controller;

import com.jiangcl.alipay.demo.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping("/pay/success")
    public String successPage(){
        System.out.println("success");
        return "success";
    }

    @RequestMapping("/pay/notifyPayResult")
    public void notifyPayResult(){
        System.out.println("notifyPayResult");
    }
}
