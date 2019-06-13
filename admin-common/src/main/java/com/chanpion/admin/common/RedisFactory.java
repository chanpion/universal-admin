/**
 * Copyright (c) 2015-2017,taotaosou All Rights Reserved.
 */

package com.chanpion.admin.common;


import org.junit.Test;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author April Chen
 * @date 2017年5月15日 上午9:53:18
 */
public class RedisFactory {

    private static Map<String, ResourceBundle> resourceBundleMap = new ConcurrentHashMap<>();

    public void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        System.out.println(bundle.getString("redis.ip"));
    }
}
