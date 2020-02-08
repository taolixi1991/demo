package com.wuhandata.wxncovblackboard.common.redis

import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.HashOperations
import org.springframework.stereotype.Component

@Component
class RedisHelper<HK, V> @Autowired constructor(@Suppress("SpringJavaInjectionPointsAutowiringInspection") redisTemplate: RedisTemplate<String, V>) {
    // 在构造器中通过redisTemplate的工厂方法实例化操作对象
    private var redisTemplate: RedisTemplate<String, V>? = redisTemplate
    // 在构造器中通过redisTemplate的工厂方法实例化操作对象
    private var hashOperations: HashOperations<String, HK, V>? = null
    private var listOperations: ListOperations<String, V>? = null
    private var zSetOperations: ZSetOperations<String, V>? = null
    private var setOperations: SetOperations<String, V>? = null
    private var valueOperations: ValueOperations<String, V>? = null

    init {
        this.hashOperations = redisTemplate.opsForHash()
        this.listOperations = redisTemplate.opsForList()
        this.zSetOperations = redisTemplate.opsForZSet()
        this.setOperations = redisTemplate.opsForSet()
        this.valueOperations = redisTemplate.opsForValue()
        redisTemplate.afterPropertiesSet()
    }

    fun hashPut(key: String, hashKey: HK, value: V) {
        hashOperations!!.put(key, hashKey, value)
    }

    fun hashPutAll(key: String, valueMap: HashMap<HK, V>) {
        hashOperations!!.putAll(key, valueMap)
    }

    fun hashContains(key: String, hashKey: Any): Boolean {
        return redisTemplate!!.hasKey(key) && hashOperations!!.hasKey(key, hashKey)
    }

    fun hashFindAll(key: String): Map<HK, V> {
        return hashOperations!!.entries(key)
    }

    fun hashGet(key: String, hashKey: Any): V? {
        return hashOperations!!.get(key, hashKey)
    }

    fun hashRemove(key: String, hashKey: Any) {
        hashOperations!!.delete(key, hashKey)
    }

    fun listPush(key: String, value: V): Long? {
        return listOperations!!.rightPush(key, value)
    }

    fun listUnshift(key: String, value: V): Long? {
        return listOperations!!.leftPush(key, value)
    }

    fun listFindAll(key: String): List<V>? {
        return if (!redisTemplate!!.hasKey(key)) {
            null
        } else listOperations!!.range(key, 0, listOperations!!.size(key)!!)
    }

    fun listLPop(key: String): V? {
        return listOperations!!.leftPop(key)
    }

    fun setValue(key: String, value: V) {
        valueOperations!!.set(key, value)
    }

    fun setValue(key: String, value: V, timeout: Long) {
        val vo = redisTemplate!!.opsForValue()
        vo.set(key, value, timeout, TimeUnit.MILLISECONDS)
    }


    fun getValue(key: String): V? {
        return valueOperations!!.get(key)
    }

    fun remove(key: String) {
        redisTemplate!!.delete(key)
    }

    fun expire(key: String, timeout: Long, timeUnit: TimeUnit): Boolean {
        return redisTemplate!!.expire(key, timeout, timeUnit)
    }
}
