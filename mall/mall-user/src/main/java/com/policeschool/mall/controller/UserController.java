package com.policeschool.mall.controller;

import com.policeschool.mall.util.ApiUtils;
import com.policeschool.mall.util.RemoteLoad;
import com.policeschool.mall.util.ResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@RestController("/user")
public class UserController extends RemoteLoad {

    @Value("${service.url.order}")
    private String serviceOrderUrl;

    @RequestMapping(value = "/testRestTemplate")
    public ResponseModel testRestTemplate(HttpServletRequest request) {
        HashMap<String, Object> par = new HashMap<>();
        //request值放到HashMap中
        ApiUtils.setRequestPar(request, par);
        //postRestObject参数说明
        //【0】服务名称 serviceOrderUrl
        //【1】接口地址 /order/getUserOrderInfo
        //【2】返回类型 ResponseModel.class
        //【3】参数类型 par
        System.out.println("ididi");
        ResponseModel res = postRestObject(serviceOrderUrl, "/order/order/getUserOrderInfo", ResponseModel.class, par);
        return res;
    }
}
