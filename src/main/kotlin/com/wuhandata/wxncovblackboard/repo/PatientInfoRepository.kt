package com.wuhandata.wxncovblackboard.repo

import com.wuhandata.wxncovblackboard.domain.PatientInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PatientInfoRepository: JpaRepository<PatientInfo, String>, JpaSpecificationExecutor<PatientInfo> {
}
