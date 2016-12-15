package org.cacheframework.cache;

import org.cacheframework.annotation.WeakCache;

/**
 * @Dscription： 弱引用工厂
 * @Author: zhangyan
 * @Date:2016/12/12.11:04
 * @Version：V1.0
 */
public interface IWeakCacheFactory extends ICacheFactory<WeakCache>{

    String WEAK_CACHE_FACTORY = "weakCacheFactory";

}
