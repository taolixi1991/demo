package com.wuhandata.wxncovblackboard.common.vo

import java.io.Serializable

class Response(
        var code: Int = 0,
        var msg: String? = "",
        var data: Any?=null
): Serializable
