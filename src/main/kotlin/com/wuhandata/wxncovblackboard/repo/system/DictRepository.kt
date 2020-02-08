package com.wuhandata.wxncovblackboard.repo.system

import com.wuhandata.wxncovblackboard.domain.system.Dict
import org.springframework.data.jpa.repository.JpaRepository

interface DictRepository: JpaRepository<Dict, Long> {

    fun findAllByParentId(id: Long): List<Dict>

    fun findAllByParentMask(mask: String): List<Dict>

    fun findByMask(mask: String): Dict?
}
