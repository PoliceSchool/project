package com.policeschool.mall.remote;

import com.policeschool.mall.util.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@FeignClient(name = "mall-order")
public interface FeignClientTest {
    /**
     * 这里是order项目中的接口地址 和接口参数
     */
    @GetMapping("/order/testFeignClient")
    ResponseModel productBrandPage(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex);
}
