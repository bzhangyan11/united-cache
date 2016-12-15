package org.cacheframework.test;


import org.cacheframework.context.ICacheKey;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/14.18:17
 * @Version：V1.0
 */
public class TestParam implements ICacheKey {

    private Integer key;

    public TestParam(Integer key){
        this.key = key;
    }

    /**
     * 缓存的键
     *
     * @return 键
     */
    @Override
    public Object key() {
        return key;
    }
}
