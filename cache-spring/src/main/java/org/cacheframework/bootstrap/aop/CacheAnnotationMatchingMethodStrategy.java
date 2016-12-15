package org.cacheframework.bootstrap.aop;

import org.cacheframework.utils.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存注解匹配策略
 * @Author: zhangyan
 * @Date:2016/12/13.18:00
 * @Version：V1.0
 */
public class CacheAnnotationMatchingMethodStrategy implements IMatchingMethodStrategy{
    /**
     * 方法是否匹配
     *
     * @param method 方法
     * @param clz    类
     * @return 是否匹配
     */
    @Override
    public boolean match(Method method, Class<?> clz) {
        return null != AnnotationUtils.findCacheMetaAnnotation(method) && void.class != method
                .getReturnType();
    }
}
