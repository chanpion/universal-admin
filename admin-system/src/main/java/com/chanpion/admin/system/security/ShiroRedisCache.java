package com.chanpion.admin.system.security;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * @author April Chen
 * @date 2019/9/10 20:46
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private static final String REDIS_SHIRO_CACHE = "shiro:session:";
    private RedisTemplate<K, V> redisTemplate;
    private String cacheKey;

    public ShiroRedisCache(String name, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        cacheKey = REDIS_SHIRO_CACHE + name + ":";

    }

    @Override
    public V get(K k) throws CacheException {
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
