package org.cacheframework.bootstrap.annotation;

import org.cacheframework.bootstrap.CacheBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Dscription： 启用缓存框架
 * @Author: zhangyan
 * @Date:2016/12/18.23:27
 * @Version：V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(CacheBootstrap.class)
public @interface EnableCache {
}
