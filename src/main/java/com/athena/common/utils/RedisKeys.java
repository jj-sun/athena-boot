package com.athena.common.utils;

/**
 * Redis所有Keys
 *
 * @author sunjie
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
