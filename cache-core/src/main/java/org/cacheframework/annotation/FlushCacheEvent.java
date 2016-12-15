package org.cacheframework.annotation;

import org.cacheframework.context.IEventType;

import java.lang.annotation.*;

/**
 * @Dscription： 缓存刷新注解
 * @Author: zhangyan
 * @Date:2016/11/29.17:27
 * @Version：V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@CacheContextEvent(eventType = FlushCacheEvent.FlushCacheEventType.class)
public @interface FlushCacheEvent {

    interface FlushCacheEventType extends IEventType {}
}
