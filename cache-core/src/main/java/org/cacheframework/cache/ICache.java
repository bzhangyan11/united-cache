package org.cacheframework.cache;

/**
 * @Dscription： 缓存容器
 * @Author: zhangyan
 * @Date:2016/11/29.17:38
 * @Version：V1.0
 */
public interface ICache{

    /**
     * 放入缓存
     * @param key 键
     * @param value 值
     */
    void put(Object key, Object value);

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    Object get(Object key);

    /**
     * 获取当前缓存的大小
     * @return 缓存大小
     */
    int size();

    /**
     * 刷新缓存
     */
    void flush();

}
