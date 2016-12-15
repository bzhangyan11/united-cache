package org.cacheframework.cache;

import java.lang.annotation.Annotation;

/**
 * @Dscription： 缓存工厂
 * @Author: zhangyan
 * @Date:2016/11/30.11:00
 * @Version：V1.0
 */
public interface ICacheFactory<A extends Annotation> {


    /**
     * 根据注解信息获取缓存
     * @param annotation 注解信息
     * @return 缓存
     */
    ICache getCache(A annotation);

}
