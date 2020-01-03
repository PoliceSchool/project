package com.policeschool.mall.controller;

import com.policeschool.mall.util.ApiUtils;
import com.policeschool.mall.util.ResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * description: 测试类
 * date: Created in 2020/1/3.
 *
 * @author lujingxiao
 */
@RestController
@RequestMapping("/order")
public class TestOrderController {
    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/index")
    public String index() {
        return "您访问的是：【" + serviceName + "】服务,【端口号】" + port;
    }

    @RequestMapping(value = "/order/getUserOrderInfo")
    public ResponseModel getUserOrderInfo(HttpServletRequest request) {
        ResponseModel responseModel = new ResponseModel(200, "成功", null);
        HashMap<String, Object> par = new HashMap<>();
        //把request中的参数放到HashMap中
        ApiUtils.setRequestPar(request, par);
        System.out.println(par.get("id") + request.getParameter("id"));
        //传怎什么值过来 就返回怎么值 检测服务之间通信是否畅通
        responseModel.setData(par);
        System.out.println("lalalalla");
        return responseModel;
    }

    @RequestMapping(value = "/testFeignClient")
    public ResponseModel testFeignClient(HttpServletRequest request) {
        ResponseModel responseModel = new ResponseModel(200,"成功",null);
        HashMap<String, Object> par = new HashMap<String, Object>();
        //把request中的参数放到HashMap中
        ApiUtils.setRequestPar(request, par);
        //传怎什么值过来 就返回怎么值 检测服务之间通信是否畅通
        responseModel.setData(par);
        System.out.println("1111111");
        return responseModel;
    }
}
