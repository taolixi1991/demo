package com.wuhandata.wxncovblackboard.repo.system

import com.wuhandata.wxncovblackboard.domain.system.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RoleRepository: JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>{

    fun findByMask(mask: String): Role?

}
