package com.wuhandata.wxncovblackboard.common.constant

enum class BodyStatus(value: String) {
    GOOD("好"),
    MEDIUM("中"),
    BAD("差");


    fun getStatus(value: String): BodyStatus {
        when(value) {
            "好" -> return GOOD
            "中" -> return MEDIUM
            "差" -> return BAD
        }
        return GOOD
    }
}
