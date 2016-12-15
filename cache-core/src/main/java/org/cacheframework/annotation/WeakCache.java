package org.cacheframework.annotation;

import org.cacheframework.cache.IWeakCacheFactory;

import java.lang.annotation.*;

/**
 * @Dscription： 弱引用缓存注解
 * @Author: zhangyan
 * @Date:2016/12/12.11:04
 * @Version：V1.0
 */
@Cache(cacheFactoryType = IWeakCacheFactory.class, cacheFactoryName = IWeakCacheFactory
        .WEAK_CACHE_FACTORY)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WeakCache {
}
