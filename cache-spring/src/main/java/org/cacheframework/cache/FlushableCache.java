package org.cacheframework.cache;


import org.cacheframework.annotation.FlushCacheEvent;
import org.cacheframework.context.ICacheContextEvent;
import org.cacheframework.context.ICacheContextListener;

/**
 * @Dscription： 可刷新的缓存
 * @Author: zhangyan
 * @Date:2016/12/14.15:05
 * @Version：V1.0
 */
public class FlushableCache extends CacheAdapter implements ICacheContextListener {
    /**
     * 缓存上下文事件
     *
     * @param cacheContextEvent 缓存上下文事件
     */
    @Override
    public void onEvent(ICacheContextEvent cacheContextEvent) {
        if(FlushCacheEvent.FlushCacheEventType.class.equals(cacheContextEvent.getEventType())){
            this.flush();
        }
    }
}
