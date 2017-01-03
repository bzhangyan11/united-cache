package org.cacheframework.context;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 空参数 cahe key 获取器
 * @Author: zhangyan
 * @Date:2016/12/13.15:01
 * @Version：V1.0
 */
@Order(10)
public class VoidCacheKeyResolver implements ICacheKeyResolver {

    private final ConcurrentMap<Method, Boolean> supportedCacheMap = new
            ConcurrentHashMap<>();

    /**
     * 是否启用该缓存处理器
     *
     * @return 是否启用
     */
    @Override
    public boolean enable() {
        return true;
    }

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method 方法
     * @return 是否支持
     */
    @Override
    public boolean support(Method method, Class<?> targetClz) {
        return checkIsSupported(method);
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
        if (this.checkIsSupported(method)) {
            return method;
        }
        else {
            throw new UnsupportedOperationException("method: " + method.getName() + " is " +
                    "Unsupported for this cache key resolver");
        }
    }

    /**
     * 检查是否支持
     *
     * @param method 方法
     * @return 是否支持
     */
    private boolean checkIsSupported(Method method) {
        Boolean isSupported = supportedCacheMap.get(method);
        if (null != isSupported) {
            return isSupported;
        }

        isSupported = ArrayUtils.isEmpty(method.getParameterTypes());

        supportedCacheMap.putIfAbsent(method, isSupported);

        return isSupported;
    }
}
