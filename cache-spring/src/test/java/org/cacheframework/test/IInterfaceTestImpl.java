package org.cacheframework.test;

import org.cacheframework.annotation.FlushCacheEvent;
import org.cacheframework.annotation.SoftCache;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2017/1/3.15:47
 * @Version：V1.0
 */
public class IInterfaceTestImpl implements IInterfaceTest {

    private int a = 0;

    @SoftCache
    @Override
    public int increment() {
        return a++;
    }

    @FlushCacheEvent
    public void flush() {
    }
}
