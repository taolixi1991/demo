package com.wuhandata.wxncovblackboard.repo

import com.wuhandata.wxncovblackboard.domain.PatientCase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PatientCaseRepository: JpaRepository<PatientCase, String>, JpaSpecificationExecutor<PatientCase> {
    fun getPatientCasesByOpenId(openId: String): List<PatientCase>
}
