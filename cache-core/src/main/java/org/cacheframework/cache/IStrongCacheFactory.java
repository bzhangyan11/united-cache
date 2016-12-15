package org.cacheframework.cache;

import org.cacheframework.annotation.StrongCache;

/**
 * @Dscription： 强引用缓存工厂
 * @Author: zhangyan
 * @Date:2016/12/12.11:15
 * @Version：V1.0
 */
@Deprecated
public interface IStrongCacheFactory extends ICacheFactory<StrongCache> {

    String STRONG_CACHE_FACTORY = "strongCacheFactory";

}
