package com.xgt.util;

import com.xgt.enums.EnumPcsServiceError;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

    /**
     * 创建成功结果
     *
     * @author liuao
     * @date 2018/4/1 14:49
     */
    public static Map<String, Object> createSuccessResult(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", EnumPcsServiceError.SUCCESS.code);
        result.put("data", data);
        result.put("success", true);
        return result;
    }

    /**
     * 创建成功结果
     *
     * @author liuao
     * @date 2018/4/1 14:49
     */
    public static Map<String, Object> createSuccessResult() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", EnumPcsServiceError.SUCCESS.code);
        result.put("success", true);
        return result;
    }


    /**
     * 创建失败结果
     *
     * @author liuao
     * @date 2018/4/1 14:49
     */
    public static Map<String, Object> createFailResult(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", EnumPcsServiceError.ERROR_OPERATE.code);
        result.put("data", message);
        result.put("success", false);
        return result;
    }

    /**
     * 自定义失败结果
     *
     * @param code
     * @return
     */
    public static Map<String, Object> createMyselfResult(String code, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        result.put("success", true);
        return result;
    }
}
