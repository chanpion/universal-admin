package com.chanpion.test;

import org.junit.Test;

import java.util.ResourceBundle;

/**
 * @author April Chen
 * @date 2019/10/14 9:09
 */
public class BundleTest {

    @Test
    public void testBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        System.out.println(bundle.getString("redis.ip"));
    }
}
