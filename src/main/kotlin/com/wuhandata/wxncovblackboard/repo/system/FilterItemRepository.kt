package com.wuhandata.wxncovblackboard.repo.system

import com.wuhandata.wxncovblackboard.domain.system.SpinnerOption
import org.springframework.data.jpa.repository.JpaRepository

interface FilterItemRepository: JpaRepository<SpinnerOption, Long> {

    fun  findAllByTypeAndParentId(type: String,parentId:String):List<SpinnerOption>

    fun  findAllByParentId(parentId: String):List<SpinnerOption>
}
