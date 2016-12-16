package org.cacheframework.test;


import org.cacheframework.annotation.FlushCacheEvent;
import org.cacheframework.annotation.SoftCache;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/14.17:04
 * @Version：V1.0
 */
public class CacheServiceTest {

    private int a = 0;

    @SoftCache
    public int autoIncrement() {
        return ++a;
    }

    @SoftCache
    public int autoIncrement2() {
        return ++a;
    }

    @SoftCache
    public int autoIncrementWithParam(TestParam testParam){
        return ++a;
    }

    @SoftCache
    public void voidReturnMethod(){
        ++a;
    }

    public int getA(){
        return a;
    }

    @FlushCacheEvent
    public void flush(){

    }

    private RuntimeException exception;

    public void setException(RuntimeException exception){
        this.exception = exception;
    }

    @SoftCache
    public void throwException(){
        throw exception;
    }

    @SoftCache
    public int testPojoParam(TestPojoParam testPojoParam){
        return ++a;
    }
}
