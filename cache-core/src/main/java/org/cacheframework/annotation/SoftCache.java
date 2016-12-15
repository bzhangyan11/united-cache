package org.cacheframework.annotation;

import org.cacheframework.cache.ISoftCacheFactory;

import java.lang.annotation.*;

/**
 * @Dscription： 软引用缓存注解
 * @Author: zhangyan
 * @Date:2016/12/12.10:48
 * @Version：V1.0
 */
@Cache(cacheFactoryType = ISoftCacheFactory.class, cacheFactoryName = ISoftCacheFactory
        .SOFT_CACHE_FACTORY)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SoftCache {

    long DEFAULT_EXPIRY = 2 * 60 * 60 * 1000;

    /**
     * 过期时间
     *
     * @return 过期秒数
     */
    long expiry() default DEFAULT_EXPIRY;

    /**
     * 开启过期功能
     *
     * @return 过期功能
     */
    boolean enableExpiry() default false;

}
