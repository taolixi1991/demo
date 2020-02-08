package com.wuhandata.wxncovblackboard.common.redis

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration

@Configuration
@EnableCaching
class RedisConfiguration: CachingConfigurerSupport() {
    @Bean
    override fun keyGenerator(): KeyGenerator? {
        return KeyGenerator { target, method, params ->
            val sb = StringBuilder()
            sb.append(target.javaClass.name)
            sb.append(method.name)
            for (obj in params) {
                sb.append(obj.toString())
            }
            sb.toString()
        }
    }

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        return RedisCacheManager.builder(connectionFactory).build()
    }

    @Bean
    fun redisConnectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration("127.0.0.1", 6379)
        return JedisConnectionFactory(config)
    }

    /**
     * @Description: 防止redis入库序列化乱码的问题
     * @return     返回类型
     * @date 2018/4/12 10:54
     */
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory)
        redisTemplate.keySerializer = StringRedisSerializer()//key序列化
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)  //value序列化
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}
