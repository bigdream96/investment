package com.fastcampus.investment.util;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    public static <T> Map<String, T> convert(T data) {
        Map<String, T> map = new HashMap<>();
        map.put("data", data);
        return map;
    }
}
