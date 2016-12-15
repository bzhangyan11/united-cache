package org.cacheframework.annotation;


import org.cacheframework.context.IEventType;

import java.lang.annotation.*;

/**
 * @Dscription: 缓存事件
 * @Author: zhangyan
 * @Date:2016/12/15.9:11
 * @Version：V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Documented
public @interface CacheContextEvent {

    /**
     * 事件名称
     * @return 事件类型名称
     */
    Class<? extends IEventType> eventType();

}
