package org.cacheframework.cache;


/**
 * @Dscription： 缓存适配基类
 * @Author: zhangyan
 * @Date:2016/12/14.15:07
 * @Version：V1.0
 */
public class CacheAdapter implements ICache{
    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(Object key, Object value) {

    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(Object key) {
        return null;
    }

    /**
     * 获取当前缓存的大小
     *
     * @return 缓存大小
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 刷新缓存
     */
    @Override
    public void flush() {

    }
}
