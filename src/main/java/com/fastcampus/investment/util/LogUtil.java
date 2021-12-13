package com.fastcampus.investment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    public static void recordLog(String message) {
        LOGGER.debug(message);
    }

    public static void recordLog(String message, String data) {
        LOGGER.debug(message + ", data=" + data);
    }
}
