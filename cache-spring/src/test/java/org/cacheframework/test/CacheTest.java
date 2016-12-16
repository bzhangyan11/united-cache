package org.cacheframework.test;


import org.cacheframework.context.InterfaceCacheKeyResolver;
import org.cacheframework.context.VoidCacheKeyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/14.16:59
 * @Version：V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:cache-test.xml")
public class CacheTest {

    @Autowired
    private CacheServiceTest cacheServiceTest;

    @Autowired
    private CacheServiceCopyTest cacheServiceCopyTest;

    @Autowired
    private VoidCacheKeyResolver voidCacheKeyResolver;

    @Autowired
    private InterfaceCacheKeyResolver interfaceCacheKeyResolver;

    /**
     * 测试不带参数的缓存
     */
    @Test
    public void testNoneArgs() {
        Assert.assertEquals(cacheServiceTest.autoIncrement(), cacheServiceTest.autoIncrement());
        Assert.assertEquals(cacheServiceTest.autoIncrement2(), cacheServiceTest.autoIncrement2());
        Assert.assertNotEquals(cacheServiceTest.autoIncrement(), cacheServiceTest.autoIncrement2());
    }

    /**
     * 测试带参数的缓存
     */
    @Test
    public void testWithArgs() {
        TestParam testParam = new TestParam(1);
        Assert.assertEquals(cacheServiceTest.autoIncrementWithParam(testParam), cacheServiceTest
                .autoIncrementWithParam(testParam));

        TestParam testParam2 = new TestParam(2);
        Assert.assertNotEquals(cacheServiceTest.autoIncrementWithParam(testParam),
                cacheServiceTest.autoIncrementWithParam(testParam2));

        Assert.assertEquals(cacheServiceTest.autoIncrementWithParam(testParam), cacheServiceTest
                .autoIncrementWithParam(testParam));

        Assert.assertEquals(cacheServiceTest.autoIncrementWithParam(testParam2), cacheServiceTest
                .autoIncrementWithParam(testParam2));
    }

    /**
     * 测试刷新
     */
    @Test
    public void testFlush() {
        int i = cacheServiceTest.autoIncrement();
        int i2 = cacheServiceTest.autoIncrement();
        cacheServiceTest.flush();
        int i3 = cacheServiceTest.autoIncrement();
        Assert.assertEquals(i, i2);
        Assert.assertNotEquals(i, i3);

        TestParam testParam = new TestParam(1);
        i = cacheServiceTest.autoIncrementWithParam(testParam);
        i2 = cacheServiceTest.autoIncrementWithParam(testParam);
        cacheServiceTest.flush();
        i3 = cacheServiceTest.autoIncrementWithParam(testParam);
        Assert.assertEquals(i, i2);
        Assert.assertNotEquals(i, i3);
    }

    /**
     * 测试键值处理器
     *
     * @throws NoSuchMethodException
     */
    @Test
    public void testKeyResolver() throws NoSuchMethodException {
        Method method = CacheServiceTest.class.getMethod("autoIncrement");
        Assert.assertEquals(this.voidCacheKeyResolver.resolve(method, null), method);

        Method method1 = CacheServiceTest.class.getMethod("autoIncrementWithParam", TestParam.class);
        Assert.assertEquals(this.interfaceCacheKeyResolver.resolve(method1, new Object[]{new
                TestParam(1)}), 1);

        Method flushMethod = CacheServiceTest.class.getMethod("flush");
        Assert.assertEquals(flushMethod, this.voidCacheKeyResolver.resolve(flushMethod, null));
    }

    /**
     * 测试空返回值场景
     */
    @Test
    public void testVoidReturnType() {
        cacheServiceTest.autoIncrement();
        int a = cacheServiceTest.getA();
        cacheServiceTest.autoIncrement();
        int a1 = cacheServiceTest.getA();
        Assert.assertEquals(a, a1);

        cacheServiceTest.voidReturnMethod();
        a = cacheServiceTest.getA();
        cacheServiceTest.voidReturnMethod();
        a1 = cacheServiceTest.getA();
        Assert.assertNotEquals(a, a1);
    }

    /**
     * 测试抛异常
     */
    @Test
    public void testThrowException() {
        this.testThrowException(new RuntimeException());
    }

    private RuntimeException testThrowException(RuntimeException e) {
        try {
            cacheServiceTest.setException(e);
            cacheServiceTest.throwException();
        } catch (RuntimeException e1) {
            Assert.assertEquals(e1, e);
        }
        return null;
    }

    /**
     * 测试两个类之间的缓存隔离
     */
    @Test
    public void testPerClass() {
        int a = this.cacheServiceTest.autoIncrement();
        int a1 = this.cacheServiceTest.autoIncrement();
        Assert.assertEquals(a, a1);

        int b = this.cacheServiceCopyTest.autoIncrement();
        int b1 = this.cacheServiceCopyTest.autoIncrement();
        Assert.assertEquals(b, b1);

        Assert.assertNotEquals(a, b1);

        cacheServiceTest.flush();

        Assert.assertNotEquals(a, this.cacheServiceTest.autoIncrement());

        Assert.assertEquals(b1, this.cacheServiceCopyTest.autoIncrement());
    }

    @Test
    public void testPOJOParam(){
        Assert.assertEquals(this.cacheServiceTest.testPojoParam(new TestPojoParam(1)),this
                .cacheServiceTest.testPojoParam(new TestPojoParam(1)));

        Assert.assertNotEquals(this.cacheServiceTest.testPojoParam(new TestPojoParam(1)),this
                .cacheServiceTest.testPojoParam(new TestPojoParam(2)));

        Assert.assertNotEquals(this.cacheServiceTest.testPojoParam(null),this.cacheServiceTest
                .testPojoParam(null));
    }
}
