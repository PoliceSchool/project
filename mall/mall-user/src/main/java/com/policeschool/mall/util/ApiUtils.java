package com.policeschool.mall.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
public class ApiUtils {
    /**
     * 把request中的数据放到map中
     */
    public static void setRequestPar(HttpServletRequest request, HashMap<String, Object> par) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            if (parameterMap.get(key) != null && parameterMap.get(key).length == 1) {
                par.put(key, parameterMap.get(key)[0]);
            }
        }
    }
}
