package com.wuhandata.wxncovblackboard.common.utils

import com.wuhandata.wxncovblackboard.common.vo.DataTableParams
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object Helper {

    fun makePage(params: DataTableParams): Pageable {
        val orderColumnNum = params.order[0]["column"]!!.toInt()
        val orderType = params.order[0]["dir"]
        val orderColumn = params.columns[orderColumnNum]["data"]
        val sort = if (orderType=="asc") Sort(Sort.Direction.ASC, orderColumn) else Sort(Sort.Direction.DESC, orderColumn)
        val pageable = PageRequest.of(params.start, params.length, sort)
        return pageable
    }
}
