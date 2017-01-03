package org.cacheframework.context;

import java.lang.reflect.Method;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/13.10:11
 * @Version：V1.0
 */
public class ToStringAnnotationCacheKeyResolver implements ICacheKeyResolver {

    /**
     * 是否启用该缓存处理器
     *
     * @return 是否启用
     */
    @Override
    public boolean enable() {
        return false;
    }

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method 方法
     * @return 是否支持
     */
    @Override
    public boolean support(Method method, Class<?> targetClz) {
        return false;
    }

    /**
     * 得出缓存 key
     *
     * @param method 方法
     * @param args   参数
     * @return 缓存key
     */
    @Override
    public Object resolve(Method method, Class<?> targetClz, Object[] args) {
        return null;
    }
}
