package org.cacheframework.context;

/**
 * @Dscription： 缓存上下文事件派发器
 * @Author: zhangyan
 * @Date:2016/12/14.13:52
 * @Version：V1.0
 */
public interface ICacheContextEventDispatcher {

    /**
     * 发送缓存事件
     * @param cacheEvent 缓存事件
     */
    void fireCacheContextEvenet(ICacheContextEvent cacheEvent);

    /**
     * 注册缓存事件监听者
     * @param cacheContextListener 缓存事件监听者
     */
    void registerCacheContextEventListener(ICacheContextListener cacheContextListener);

}
