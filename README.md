# united-cache
缓存框架 </br>
### cache-core 
</br>
定义了一些框架的基础抽象概念以及缓存元注解，缓存事件元注解</br>
### cache-spring
</br>
提供了与spring相集成的相关功能</br>
如果以XML方式进行配置，可以在配置文件中引入</br>
bean class="org.cacheframework.bootstrap.CacheBootstrap" </br>
如果以注解方式进行配置，可以在配置类上打上注解@EnableCache<br>
具体使用可以看测试用例</br><br>
扩展性

启动器类org.cacheframework.bootstrap.CacheBootstrap下的所有提供的组件都是可以复写实现的，整个架构是松散，高内聚，低耦合的，另外还提供自定义的缓存事件，一个类实例范围内有一条缓存事件总线，可以自定义缓存事件，通过注解的方式发送缓存事件，可以通过向缓存事件总线注册监听来监听缓存事件。<br>
本缓存框架的缓存组件和缓存组件都是可以通过以元注解形式来进行扩展，自带的开箱即用的几个缓存组件是通过这种方式扩展而来，自带的刷新缓存事件也是通过此种方式来实现的。详例请看@SoftCache,@FlushCacheEvent。两个注解扩展。
### cache-guice
</br>
提供了与guice的相关集成（待开发）</br>
