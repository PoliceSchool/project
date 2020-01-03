package com.policeschool.mall.util;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@Component
public class RemoteLoad {
    @Autowired
    private RestTemplate rest;

    public <T> T postRestObject(String serviceName, String url, Class<T> clazz, Map<String, Object> pars) {
        return postRestObjectRetry(serviceName, url, clazz, pars, 0);
    }

    private <T> T postRestObjectRetry(String serviceName, String url, Class<T> clazz, Map<String, Object> pars,
                                      Integer retryTime) {
        try {
            T data = rest.postForObject(Joiner.on("").join("http://", serviceName, url), convertPar(pars), clazz);
            return data;
        } catch (Exception e) {
            // TODO: handle exception
            if (retryTime < 3) {
                return postRestObjectRetry(serviceName, url, clazz, pars, retryTime + 1);
            }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把map放到MultiValueMap中 用来传值
     */
    private MultiValueMap<String, Object> convertPar(Map<String, Object> par) {
        MultiValueMap<String, Object> result = new LinkedMultiValueMap<String, Object>();
        if (par == null) {
            return result;
        }
        for (String key : par.keySet()) {
            result.add(key, par.get(key));
        }
        return result;
    }
}
