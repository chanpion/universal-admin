package com.chanpion.admin.system.security;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author April Chen
 * @date 2019/9/10 20:46
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private static final String REDIS_SHIRO_CACHE = "shiro:session:";
    private RedisTemplate<K, V> redisTemplate;

    private String keyPrefix;
    /**
     * 过期时间30分钟
     */
    private long globExpire = 30;

    public ShiroRedisCache(String name, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        keyPrefix = REDIS_SHIRO_CACHE + name + ":";

    }

    @Override
    public V get(K k) throws CacheException {
        redisTemplate.expire(getCacheKey(k), globExpire, TimeUnit.MINUTES);
        return redisTemplate.opsForValue().get(getCacheKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(getCacheKey(k), v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        if (k == null) {
            return null;
        }
        if (k instanceof String) {
            return (K) (this.keyPrefix + k.toString());
        }
        return (K) k;
    }
}
