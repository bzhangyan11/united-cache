package org.cacheframework.cache;

import org.cacheframework.annotation.SoftCache;

/**
 * @Dscription： 软引用缓存工厂
 * @Author: zhangyan
 * @Date:2016/12/12.10:48
 * @Version：V1.0
 */
public interface ISoftCacheFactory extends ICacheFactory<SoftCache>{

    String SOFT_CACHE_FACTORY = "softCacheFactory";

}
