package com.wuhandata.wxncovblackboard.common.vo

class DataTableParams(
        var draw: Int = 0,
        var columns: List<Map<String, String>> = ArrayList(),
        var order: List<Map<String, String>> = ArrayList(),
        var start: Int = 0,
        var length: Int = 0,
        var search: Map<String, String> = HashMap()
)
