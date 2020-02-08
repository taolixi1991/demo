package com.wuhandata.wxncovblackboard.repo

import com.wuhandata.wxncovblackboard.domain.CaseDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CaseDetailRepository: JpaRepository<CaseDetail, String>, JpaSpecificationExecutor<CaseDetail> {
}
