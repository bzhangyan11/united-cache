package org.cacheframework.annotation;


import org.cacheframework.cache.ICacheFactory;
import java.lang.annotation.*;

/**
 * @Dscription： 缓存元注解
 * @Author: zhangyan
 * @Date:2016/11/29.17:27
 * @Version：V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Documented
public @interface Cache {

    /**
     * 缓存解析器类型
     * @return 缓存解析器class
     */
    Class<? extends ICacheFactory> cacheFactoryType();

    /**
     * 缓存容器类型
     * @return 缓存容器名
     */
    String cacheFactoryName();

}
