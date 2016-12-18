package org.cacheframework.test;

import org.cacheframework.bootstrap.annotation.EnableCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/18.23:31
 * @Version：V1.0
 */
@EnableCache
@Configuration
public class CacheAnnotationConfig {

    @Bean
    public CacheServiceTest cacheServiceTest(){
        return new CacheServiceTest();
    }

}
