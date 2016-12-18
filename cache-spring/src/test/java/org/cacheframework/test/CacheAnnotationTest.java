package org.cacheframework.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/18.23:31
 * @Version：V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheAnnotationConfig.class)
public class CacheAnnotationTest {

    @Autowired
    private CacheServiceTest cacheServiceTest;

    /**
     * 测试不带参数的缓存
     */
    @Test
    public void testNoneArgs() {
        Assert.assertEquals(cacheServiceTest.autoIncrement(), cacheServiceTest.autoIncrement());
        Assert.assertEquals(cacheServiceTest.autoIncrement2(), cacheServiceTest.autoIncrement2());
        Assert.assertNotEquals(cacheServiceTest.autoIncrement(), cacheServiceTest.autoIncrement2());
    }

}
