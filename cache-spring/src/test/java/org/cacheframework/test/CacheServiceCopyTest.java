package org.cacheframework.test;


import org.cacheframework.annotation.SoftCache;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/15.10:55
 * @Version：V1.0
 */
public class CacheServiceCopyTest {

    private int a = 100;

    @SoftCache
    public int autoIncrement() {
        return ++a;
    }

}
