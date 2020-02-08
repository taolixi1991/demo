package com.wuhandata.wxncovblackboard.common.vo

import java.io.Serializable

class DataTable<T>(
        var draw: Int = 0,
        var recordsTotal: Long = 0L,
        var recordsFiltered: Long = 0L,
        var data: List<T> = ArrayList()
): Serializable
