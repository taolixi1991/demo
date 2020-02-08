package com.wuhandata.wxncovblackboard.repo

import com.wuhandata.wxncovblackboard.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface CustomerRepository: JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
    fun findByOpenId(openId: String): Optional<Customer>
}
