package org.cacheframework.context;



import org.cacheframework.cache.ICache;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存上下文
 * @Author: zhangyan
 * @Date:2016/12/13.11:16
 * @Version：V1.0
 */
public interface ICacheContext {

    /**
     * 获取缓存
     *
     * @param method 方法
     * @return 缓存
     */
    ICache getCache(Method method, Class<?> clz);


}
