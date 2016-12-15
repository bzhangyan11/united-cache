package org.cacheframework.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 软引用缓存
 * @Author: zhangyan
 * @Date:2016/12/14.15:02
 * @Version：V1.0
 */
public class GlobalSoftCache extends FlushableCache {

    private final Object lock = new Object();

    private volatile Reference<ConcurrentMap<Object, Object>> cacheMapRef;

    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(Object key, Object value) {
        this.getCacheMap().put(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(Object key) {
        return this.getCacheMap().get(key);
    }

    /**
     * 获取当前缓存的大小
     *
     * @return 缓存大小
     */
    @Override
    public int size() {
        return this.getCacheMap().size();
    }

    /**
     * 刷新缓存
     */
    @Override
    public void flush() {
        this.getCacheMap().clear();
    }

    /**
     * 获得缓存map
     *
     * @return 缓存map
     */
    private ConcurrentMap<Object, Object> getCacheMap() {
        ConcurrentMap<Object, Object> cacheMap = null;
        if (null != this.cacheMapRef) {
            cacheMap = this.cacheMapRef.get();
        }

        if (null != cacheMap) {
            return cacheMap;
        }

        synchronized (this.lock) {
            if (null != this.cacheMapRef) {
                cacheMap = this.cacheMapRef.get();
            }

            if (null != cacheMap) {
                return cacheMap;
            }

            cacheMap = new ConcurrentHashMap<>();

            this.cacheMapRef = new SoftReference<>(cacheMap);
        }

        return cacheMap;
    }
}
