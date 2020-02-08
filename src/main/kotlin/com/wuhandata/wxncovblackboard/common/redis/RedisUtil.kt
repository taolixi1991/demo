package com.wuhandata.wxncovblackboard.common.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisUtil {
    @Autowired
    lateinit var redisHelper: RedisHelper<String, String>

    private val expireTimeMap = mapOf(
            "VERCODE" to 10L
    )

    fun save(redisKey: String, valueMap: HashMap<String, String>) {
        redisHelper.hashPutAll(redisKey, valueMap)
        expireTimeMap[redisKey]?.let { redisHelper.expire(redisKey, 10, TimeUnit.MINUTES) }
    }

    fun contains(redisKey: String, key: String): Boolean {
        return redisHelper.hashContains(redisKey, key)
    }

    fun get(redisKey: String, key: String): String? {
        return redisHelper.hashGet(redisKey, key)
    }

    fun delete(redisKey: String, key: String) {
        redisHelper.hashRemove(redisKey, key)
    }

}
