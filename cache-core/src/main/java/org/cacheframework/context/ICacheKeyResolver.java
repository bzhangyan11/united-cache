package org.cacheframework.context;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存key处理器
 * @Author: zhangyan
 * @Date:2016/12/13.9:59
 * @Version：V1.0
 */
public interface ICacheKeyResolver {

    /**
     * 是否启用该缓存处理器
     *
     * @return 是否启用
     */
    boolean enable();

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method    方法
     * @param targetClz 调用对象
     * @return 是否支持
     */
    boolean support(Method method, Class<?> targetClz);

    /**
     * 得出缓存 key
     *
     * @param method 方法
     * @param args   参数
     * @return 缓存key
     */
    Object resolve(Method method, Class<?> targetClz, Object[] args);

}
