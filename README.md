# cache
缓存框架
cache-core
定义了一些框架的基础抽象概念以及缓存元注解，缓存事件元注解。
cache-spring
提供了与spring相集成的相关功能。
需要启动可以在xml配置文件中加入
<bean class="org.cacheframework.bootstrap.CacheBootstrap"/>
具体使用可以看测试用例
cache-guice
提供了与guice的相关集成。（待开发）
