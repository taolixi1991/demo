package com.wuhandata.wxncovblackboard.repo.system

import com.wuhandata.wxncovblackboard.domain.system.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    fun findByUsername(username: String): User?

}
