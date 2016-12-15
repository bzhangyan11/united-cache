package org.cacheframework.annotation;

import org.cacheframework.cache.IStrongCacheFactory;

import java.lang.annotation.*;

/**
 * @Dscription： 强引用缓存注解
 * @Author: zhangyan
 * @Date:2016/12/12.11:15
 * @Version：V1.0
 */
@Cache(cacheFactoryType = IStrongCacheFactory.class, cacheFactoryName = IStrongCacheFactory
        .STRONG_CACHE_FACTORY)
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface StrongCache {
}
